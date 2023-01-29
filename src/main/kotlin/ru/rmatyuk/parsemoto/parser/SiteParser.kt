package ru.rmatyuk.parsemoto.parser

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.enums.TypePhoto
import ru.rmatyuk.parsemoto.service.FileService
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import ru.rmatyuk.parsemoto.dto.*
import ru.rmatyuk.parsemoto.service.HttpService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Component
class SiteParser(fileService: FileService, httpService: HttpService) {

    private final val fileService: FileService
    private final val httpService: HttpService

    private final val baseUrl = "https://cemeco.ru"
    var logger: Logger = LoggerFactory.getLogger(SiteParser::class.java)

    init {
        this.fileService = fileService
        this.httpService = httpService
    }

    fun parseListMoto(html: String): List<MotoDto> {
        val result = mutableListOf<MotoDto>()
        try {
            val rows = HTMLParser.getElementsByClass(html, "t-product-list__row").drop(1)
            rows.forEach { row ->
                try {
                    val moto = MotoDto()
                    val photoElement = row.getElementsByClass("t-product-list__item _photo _download")[0]
                    val modelElement = row.getElementsByClass("t-product-list__item _model")[0]
                    moto.url = baseUrl + photoElement.getElementsByTag("a").attr("href")
                    logger.info("Parse moto in list with url = ${moto.url}")
                    moto.lotNumber = row.getElementsByClass("t-product-list__item _code").text().trim()
                    val mark = modelElement.getElementsByTag("b")[0].text().trim()
                    val model = modelElement.text().trim().split(mark).last().trim()
                    moto.mark = mark
                    moto.model = model
                    val urlPhotoMain = baseUrl + photoElement.getElementsByTag("img")[0].attr("src")
                    val motoPhotoEntity = MotoPhotoDto(urlMain = urlPhotoMain, typePhoto = TypePhoto.PREVIEW)
                    moto.photos = mutableListOf(motoPhotoEntity)
                    result.add(moto)
                } catch (ex: Exception) {
                    logger.error("Row not parse ${row.text()}. ${ex.message}")
                }
            }
        } catch (ex: Exception) {
            logger.error("Parse list moto failed. ${ex.message}")
        }
        return result
    }

    fun parseMotoPageMainInfo(html: String, moto: MotoDto): MotoDto {
        try {
            val details = HTMLParser.getElementsByClass(html, "b-product-detail").first()!!
            val photosElements = HTMLParser.getElementsByClass(html, "owl-thumb-item")
            val rowTableDetails = details.getElementsByClass("b-product-params").first()!!.getElementsByTag("tr")
            val rowTableRating = details.getElementsByClass("b-product-rating").first()!!.getElementsByTag("tr")
            val productStartPrice = details.getElementsByClass("b-product-buy__start").first()!!
            val productEndPrice = details.getElementsByClass("b-product-buy__end").first()!!

            moto.vendorCode = getVendorCode(details)
            moto.year = getDigit(getValueInTable(rowTableDetails, "Год:"))
            moto.mileage = getDigit(getValueInTable(rowTableDetails, "Пробег:")).toLong()
            moto.power = getDigit(getValueInTable(rowTableDetails, "Объем:"))
            moto.frameNumber = getValueInTable(rowTableDetails, "Номер рамы:")
            moto.grade = getDouble(getValueInTable(rowTableDetails, "Оценка:"))
            moto.dataAuction = getLocalDate(getValueInTable(rowTableDetails, "Дата торгов:"))
            moto.auction = getValueInTable(rowTableDetails, "Аукцион:")

            moto.engineGrade = getDigit(getValueInTable(rowTableRating, "Оценка двигателя:"))
            moto.electronicsGrade = getDigit(getValueInTable(rowTableRating, "Оценка электроники:"))
            moto.frameGrade = getDigit(getValueInTable(rowTableRating, "Оценка рамы:"))
            moto.appearanceGrade = getDigit(getValueInTable(rowTableRating, "Внешний вид:"))
            moto.frontGrade = getDigit(getValueInTable(rowTableRating, "Передняя часть:"))
            moto.rearGrade = getDigit(getValueInTable(rowTableRating, "Задняя часть:"))

            moto.startPriceJpy = getDigit(productStartPrice.getElementsByClass("b-product-price__item _yen").text()).toLong()
            moto.startPriceRub = getDigit(productStartPrice.getElementsByClass("b-product-price__item _rur").text()).toLong()
            moto.endPriceJpy = getDigit(productEndPrice.getElementsByClass("b-product-price__item _yen").text()).toLong()
            moto.endPriceRub = getDigit(productEndPrice.getElementsByClass("b-product-price__item _rur").text()).toLong()

            moto.status = getStatus(details)
            moto.photos.addAll(getPhotos(photosElements, TypePhoto.MAIN))
            logger.info("Parse moto main info done")
        } catch (ex: Exception) {
            logger.error("Parse moto main info failed. ${ex.message}")
        }
        return moto
    }

    fun parseMotoPageAdditionalInfoAuction(html: String, moto: MotoDto): MotoDto {
        try {
            val addInfoAuction = HTMLParser.getElementsByClass(html, "b-auction-more").first()!!
            val addInfoList = mutableListOf<MotoAdditionalInfoAuctionDto>()
            val rows = addInfoAuction.getElementsByClass("b-auction-more__row")
            rows.forEach { row ->
                val name = row.getElementsByClass("b-auction-more__head").text().trim()
                val list = row.getElementsByClass("b-auction-more__list")
                val addInfoValue = mutableListOf<MotoAdditionalInfoAuctionValueDto>()
                var description = ""
                if (list.isEmpty()) {
                    description = getDescriptionAddInfoAuction(row, name)
                } else {
                    val listItems = list.first()!!.getElementsByClass("b-auction-more__list-item")
                    listItems.forEach { item ->
                        val itemName = item.getElementsByTag("span").text().trim()
                        val itemDescription = getDescriptionAddInfoAuction(item, itemName)
                        addInfoValue.add(MotoAdditionalInfoAuctionValueDto(itemName, itemDescription))
                    }
                }
                val addInfo = MotoAdditionalInfoAuctionDto(name, description, addInfoValue)
                addInfoList.add(addInfo)
            }
            moto.additionalInfoAuction = addInfoList
            logger.info("Parse moto page additional info auction done")
        } catch (ex: Exception) {
            logger.error("Parse moto page additional info auction failed. ${ex.message}")
        }
        return moto
    }

    fun parseMotoPageAdditionalInfo(html: String, moto: MotoDto): MotoDto {
        try {
            val motoAdditionalInfoList = mutableListOf<MotoAdditionalInfoDto>()
            val rows = HTMLParser.getElementsByClass(html, "b-item-state")
            rows.forEach { row ->
                val infoValues = mutableListOf<MotoAdditionalInfoValueDto>()
                val split = row.getElementsByClass("b-item-state__title").text().split(":")
                val name = split.first().trim()
                val grade = getDigit(split.last().trim())
                val photoRows = row.getElementsByClass("b-image-row__item")
                val photos = getPhotosAddInfo(photoRows)
                val paramRows = row.getElementsByClass("b-params-row__item")
                paramRows.forEach { paramRow ->
                    val allTextParam = paramRow.text()
                    val paramName = paramRow.getElementsByTag("span").text()
                    var paramDescription = ""
                    if (!allTextParam.endsWith(paramName)) {
                        paramDescription = allTextParam.split(paramName).last().trim()
                    }
                    infoValues.add(MotoAdditionalInfoValueDto(paramName.trim(), paramDescription))
                }
                motoAdditionalInfoList.add(MotoAdditionalInfoDto(name, grade, infoValues, photos))
            }

            moto.additionalInfo = motoAdditionalInfoList
            logger.info("Parse moto page additional info done")
        } catch (ex: Exception) {
            logger.error("Parse moto page additional info failed. ${ex.message}")
        }
        return moto
    }

    private fun getDescriptionAddInfoAuction(row: Element?, name: String): String {
        return row!!.text().split(name).last().trim()
    }

    private fun getVendorCode(element: Element): String {
        val allText = element.getElementsByClass("b-product-title__brand").text().trim()
        return if (allText.contains("арт.")) {
            allText.split("арт.").last().trim()
        } else {
            ""
        }
    }

    private fun getStatus(element: Element): String {
        return element.getElementsByClass("b-product-buy__status").first()!!
                .getElementsByClass("b-product-status").first()!!
                .getElementsByTag("span").first()!!.text().trim()
    }

    private fun getValueInTable(elements: Elements, name: String): String {
        elements.forEach {
            val td = it.getElementsByTag("td")
            if (td.first()!!.text().trim() == name) {
                return td.last()!!.text().trim()
            }
        }
        return ""
    }

    private fun getDigit(string: String): Int {
        val strDigit = string.filter { it.isDigit() }
        return if (strDigit.isEmpty()) {
            0
        } else {
            strDigit.toInt()
        }
    }

    private fun getLocalDate(string: String): LocalDate? {
        return try {
            LocalDate.parse(string, DateTimeFormatter.ofPattern("uuuu.MM.dd"))
        } catch (ex: DateTimeParseException) {
            null
        }
    }

    private fun getDouble(string: String): Double {
        return if (string.contains(".") || string.contains(",")){
            val tmp = string.replace(",", ".")
            val split = tmp.split(".")
            "${getDigit(split.first())}.${getDigit(split.last())}".toDouble()
        } else {
            getDigit(string).toDouble()
        }
    }

    private fun getPhotos(elements: Elements, typePhoto: TypePhoto): MutableList<MotoPhotoDto> {
        val photos = mutableListOf<MotoPhotoDto>()
        elements.forEach {
            val elementsByTag = it.getElementsByTag("img")
            var urlPhotoMain = elementsByTag.attr("data-wpfc-original-src")
            if (urlPhotoMain == "") {
                urlPhotoMain = elementsByTag.attr("src")
            }
            photos.add(MotoPhotoDto(urlPhotoMain, typePhoto))
        }
        return photos
    }

    private fun getPhotosAddInfo(elements: Elements): MutableList<MotoAdditionalInfoPhotoDto> {
        val photos = mutableListOf<MotoAdditionalInfoPhotoDto>()
        elements.forEach {
            val elementsByTag = it.getElementsByTag("img")
            var urlPhotoMain = elementsByTag.attr("data-wpfc-original-src")
            if (urlPhotoMain == "") {
                urlPhotoMain = elementsByTag.attr("src")
            }
            urlPhotoMain = baseUrl + urlPhotoMain
            photos.add(MotoAdditionalInfoPhotoDto(urlPhotoMain))
        }
        return photos
    }

    fun getInfoStartPage(url: String): InfoStartPage {
        try {
            val get = httpService.get(url)
            val countPage = HTMLParser.getElementsByClass(get, "last")[1].text().trim().toInt()
            val countMoto = getDigit(HTMLParser.getElementsByClass(get, "b-category-head__count")[0]
                    .getElementsByTag("span")[0].text())
            logger.info("Count all moto $countMoto")
            logger.info("Count page $countPage on $url")
            return InfoStartPage(countPage, countMoto)
        } catch (ex: Exception) {
            logger.error("Not get count page on $url. ${ex.message}")
        }
        return InfoStartPage(0, 0)
    }

    fun parseByUrlPath(url: String): List<MotoDto> {
        logger.info("Start parse page moto list $url")
        val motoDtoList = mutableListOf<MotoDto>()
        try {
            val body = httpService.get(url)
            motoDtoList.addAll(parseListMoto(body))
            for (i in 0 until motoDtoList.size) {
                motoDtoList[i] = parseMotoPage(motoDtoList[i])
            }
            return motoDtoList
        } catch (ex: Exception) {
            logger.error("Not parse page $url")
        }
        return motoDtoList
    }

    private fun parseMotoPage(motoDto: MotoDto): MotoDto {
        var resultMotoDto = motoDto
        try {
            if (motoDto.url != null) {
                val body = httpService.get(motoDto.url!!)
                resultMotoDto = parseMotoPageMainInfo(body, resultMotoDto)
                resultMotoDto = parseMotoPageAdditionalInfoAuction(body, resultMotoDto)
                resultMotoDto = parseMotoPageAdditionalInfo(body, resultMotoDto)
                resultMotoDto.new = true
            } else {
                logger.warn("No url $motoDto")
            }
        } catch (ex: Exception) {
            logger.error("Error in parse moto page with ${motoDto.url}")
        }
        return resultMotoDto
    }
}