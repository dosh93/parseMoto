package ru.rmatyuk.parsemoto.service.motoOnline

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.rmatyuk.parsemoto.dto.*
import ru.rmatyuk.parsemoto.entity.AuctionEntity
import ru.rmatyuk.parsemoto.entity.MarkEntity
import ru.rmatyuk.parsemoto.entity.ModelEntity
import ru.rmatyuk.parsemoto.entity.StatusEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.*
import ru.rmatyuk.parsemoto.parser.SiteParser
import ru.rmatyuk.parsemoto.repository.motoOnline.MotoOnlineRepository
import ru.rmatyuk.parsemoto.service.*
import java.util.*

@Component
class MotoOnlineService(
        motoOnlineRepository: MotoOnlineRepository, motoPhotoOnlineService: MotoPhotoOnlineService,
        commonService: CommonService
) {
    private final val motoOnlineRepository: MotoOnlineRepository
    private final val motoPhotoOnlineService: MotoPhotoOnlineService
    private final val commonService: CommonService
    var logger: Logger = LoggerFactory.getLogger(MotoOnlineService::class.java)

    init {
        this.motoOnlineRepository = motoOnlineRepository
        this.motoPhotoOnlineService = motoPhotoOnlineService
        this.commonService = commonService
    }

    @Transactional
    fun saveMotoOnline(motos: List<MotoDto>): Int {
        var countSave = 0
        motos.forEach {
            try {
                motoOnlineRepository.deleteByUrl(it.url!!)
                if (it.new != null) {
                    var motoEntity = it.toMotoOnlineEntity()

                    val commonFiled = commonService.saveCommonFiled(it.auction!!, it.status!!, it.mark!!, it.model!!)
                    motoEntity.mark = commonFiled.mark
                    motoEntity.model = commonFiled.model
                    motoEntity.auction = commonFiled.auction
                    motoEntity.status = commonFiled.status

                    motoEntity.additionalInfoJson = Json.encodeToString(it.additionalInfo)
                    motoEntity.additionalInfoAuctionJson = Json.encodeToString(it.additionalInfoAuction)
                    motoEntity = motoOnlineRepository.save(motoEntity)

                    motoPhotoOnlineService.saveAll(it.photos, motoEntity)
                    countSave++
                    logger.info("Moto save with url ${motoEntity.url}")
                } else {
                    logger.warn("Moto not save with url ${it.url}")
                }
            } catch (ex: Exception) {
                logger.error("Moto not save with url ${it.url}. ${ex.message}")
            }
        }
        return countSave
    }

    fun MotoDto.toMotoOnlineEntity(): MotoOnlineEntity {
        return MotoOnlineEntity(this.lotNumber!!, this.dataAuction, this.year, this.power, this.frameNumber, this.mileage,
                this.grade, this.startPriceJpy, this.startPriceRub, this.endPriceJpy, this.endPriceRub, this.vendorCode,
                this.engineGrade, this.electronicsGrade, this.electronicsGrade, this.appearanceGrade, this.frontGrade,
                this.rearGrade, this.url, this.new)
    }

    fun getById(id: String): MotoDto {
        return motoOnlineRepository.findById(UUID.fromString(id)).orElse(MotoOnlineEntity()).toMotoDto()
    }

    fun MotoOnlineEntity.toMotoDto(): MotoDto {
        return MotoDto(
                this.lotNumber, this.mark!!.name, this.model!!.name, this.url, this.new, this.photos.toMotoPhotoDto(),
                this.auction!!.name, this.dataAuction!!, this.year, this.power, this.frameNumber, this.mileage,
                this.grade, this.startPriceJpy, this.startPriceRub, this.endPriceJpy, this.endPriceRub,
                this.status!!.name, this.vendorCode, this.engineGrade, this.electronicsGrade, this.frameGrade,
                this.appearanceGrade, this.frontGrade, this.rearGrade,
        )
    }

    fun MutableList<MotoPhotoOnlineEntity>.toMotoPhotoDto(): MutableList<MotoPhotoDto> {
        return this.map { MotoPhotoDto(it.urlMain, it.typePhoto) }.toMutableList()
    }

    @Transactional
    fun setAllMotoNew(new: Boolean): Boolean {
        try {
            motoOnlineRepository.setAllNew(new)
            logger.info("All moto set new = $new")
            return true
        }catch (ex: Exception) {
            logger.error("Not set new all moto. ${ex.message}")
        }
        return false
    }

    @Transactional
    fun deleteAllOldMoto() {
        try {
            motoOnlineRepository.deleteAll(motoOnlineRepository.findByNew(false))
            logger.info("All moto old deleted")
        }catch (ex: Exception) {
            logger.error("All moto old not deleted")
        }
    }

}