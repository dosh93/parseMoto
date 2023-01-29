package ru.rmatyuk.parsemoto.service.motoOnline

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.rmatyuk.parsemoto.dto.*
import ru.rmatyuk.parsemoto.entity.motoOnline.*
import ru.rmatyuk.parsemoto.enums.TypePhoto
import ru.rmatyuk.parsemoto.repository.motoOnline.MotoOnlineRepository
import ru.rmatyuk.parsemoto.service.*

@Component
class MotoOnlineService(
        motoOnlineRepository: MotoOnlineRepository, commonService: CommonService
) {
    private final val motoOnlineRepository: MotoOnlineRepository
    private final val commonService: CommonService
    var logger: Logger = LoggerFactory.getLogger(MotoOnlineService::class.java)

    init {
        this.motoOnlineRepository = motoOnlineRepository
        this.commonService = commonService
    }

    @Transactional
    fun saveMotoOnline(motos: List<MotoDto>): Int {
        var countSave = 0
        val motoToSave = mutableListOf<MotoOnlineEntity>()
        motos.forEach {
            try {
                motoOnlineRepository.deleteByUrl(it.url!!)
                if (it.new != null) {
                    val motoEntity = it.toMotoOnlineEntity()

                    val commonFiled = commonService.saveCommonFiled(it.auction!!, it.status!!, it.mark!!, it.model!!)
                    motoEntity.mark = commonFiled.mark
                    motoEntity.model = commonFiled.model
                    motoEntity.auction = commonFiled.auction
                    motoEntity.status = commonFiled.status

                    motoEntity.additionalInfoJson = Json.encodeToString(it.additionalInfo)
                    motoEntity.additionalInfoAuctionJson = Json.encodeToString(it.additionalInfoAuction)
                    motoEntity.photosPreview = it.photos.first { photo -> photo.typePhoto == TypePhoto.PREVIEW }.urlMain
                    motoEntity.photosMainJson = Json.encodeToString(it.photos.filter { photo -> photo.typePhoto == TypePhoto.MAIN })
                    motoToSave.add(motoEntity)
                    countSave++
                    logger.info("Moto add to save with url ${motoEntity.url}")
                } else {
                    logger.warn("Moto not save with url ${it.url}")
                }
            } catch (ex: Exception) {
                logger.error("Moto not save with url ${it.url}. ${ex.message}")
            }
        }
        try {
            motoOnlineRepository.saveAll(motoToSave)
        } catch (ex: Exception) {
            logger.error("Not save moto with urls ${motoToSave.map { it.url }.joinToString(separator = ", ")}")
        }
        return countSave
    }

    fun MotoDto.toMotoOnlineEntity(): MotoOnlineEntity {
        return MotoOnlineEntity(this.lotNumber!!, this.dataAuction, this.year, this.power, this.frameNumber, this.mileage,
                this.grade, this.startPriceJpy, this.startPriceRub, this.endPriceJpy, this.endPriceRub, this.vendorCode,
                this.engineGrade, this.electronicsGrade, this.electronicsGrade, this.appearanceGrade, this.frontGrade,
                this.rearGrade, this.url, this.new)
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