package ru.rmatyuk.parsemoto.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.ConfigUrl
import ru.rmatyuk.parsemoto.controller.TestController
import ru.rmatyuk.parsemoto.dto.MotoDto
import ru.rmatyuk.parsemoto.parser.SiteParser
import ru.rmatyuk.parsemoto.service.moto.MotoService
import ru.rmatyuk.parsemoto.service.motoOnline.MotoOnlineService
import java.time.LocalDateTime

@Component
class TaskService(motoOnlineService: MotoOnlineService, motoService: MotoService, siteParser: SiteParser, configUrl: ConfigUrl) {
    private final val motoOnlineService: MotoOnlineService
    private final val motoService: MotoService
    private final val siteParser: SiteParser
    private final val configUrl: ConfigUrl

    var logger: Logger = LoggerFactory.getLogger(TaskService::class.java)

    val urlTemplateOnline = configUrl.urlTemplateOnline
    val urlTemplateHistory = configUrl.urlTemplateHistory

    init {
        this.motoOnlineService = motoOnlineService
        this.motoService = motoService
        this.siteParser = siteParser
        this.configUrl = configUrl
    }

    fun onlineAuction(startPage: Int = 1, countPage: Int? = null) {
        logger.info("Start task online auction ${LocalDateTime.now()}")
        var countPageCurrent = countPage
        var url = urlTemplateOnline.format(startPage)
        val success = motoOnlineService.setAllMotoNew(false)
        if (success) {
            if (countPageCurrent == null) {
                countPageCurrent = siteParser.getInfoStartPage(url).countPage
            }
            var allSave = 0
            for (i in startPage until countPageCurrent + startPage) {
                url = urlTemplateOnline.format(i)
                val listMotoDto: List<MotoDto> = siteParser.parseByUrlPath(url)
                val countSaveInPage = motoOnlineService.saveMotoOnline(listMotoDto)
                allSave += countSaveInPage
                logger.info("Save $countSaveInPage moto in page №$i. All save $allSave")
            }
            motoOnlineService.deleteAllOldMoto()
        }
        logger.info("End task online auction ${LocalDateTime.now()}")
    }

    fun historyAuction(startPage: Int = 1, countPage: Int? = null) {
        logger.info("Start task history auction ${LocalDateTime.now()}")
        var countPageCurrent = countPage
        var url = urlTemplateHistory.format(startPage)
        if (countPageCurrent == null) {
            countPageCurrent = siteParser.getInfoStartPage(url).countPage
        }
        var allSave = 0
        for (i in startPage until countPageCurrent + startPage) {
            url = urlTemplateHistory.format(i)
            val listMotoDto: List<MotoDto> = siteParser.parseByUrlPath(url)
            val countSaveInPage = motoService.saveMotoHistory(listMotoDto)
            allSave += countSaveInPage
            logger.info("Save $countSaveInPage moto in page №$i. All save $allSave")
            if (countSaveInPage == 0) return
        }
        logger.info("End task history auction ${LocalDateTime.now()}")
    }
}