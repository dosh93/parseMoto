package ru.rmatyuk.parsemoto.service.moto

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoDto
import ru.rmatyuk.parsemoto.dto.MotoDto
import ru.rmatyuk.parsemoto.entity.AuctionEntity
import ru.rmatyuk.parsemoto.entity.MarkEntity
import ru.rmatyuk.parsemoto.entity.ModelEntity
import ru.rmatyuk.parsemoto.entity.StatusEntity
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import ru.rmatyuk.parsemoto.enums.TypePhoto
import ru.rmatyuk.parsemoto.repository.moto.MotoRepository
import ru.rmatyuk.parsemoto.service.*
import ru.rmatyuk.parsemoto.service.motoOnline.MotoOnlineService

@Component
class MotoService(motoRepository: MotoRepository, commonService: CommonService) {

    private final val motoRepository: MotoRepository
    private final val commonService: CommonService

    var logger: Logger = LoggerFactory.getLogger(MotoService::class.java)

    init {
        this.motoRepository = motoRepository
        this.commonService = commonService
    }

    @Transactional
    fun saveMotoHistory(motos: List<MotoDto>): Int {
        var countSave = 0
        val motoToSave = mutableListOf<MotoEntity>()
        motos.forEach {
            try {
                val moto = motoRepository.findFirstByUrl(it.url!!)
                if (moto == null) {
                    val motoEntity = it.toMotoEntity()
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
                    logger.info("Moto exit in DB url ${it.url}")
                }
            } catch (ex: Exception) {
                logger.error("Moto not save with url ${it.url}. ${ex.message}")
            }
        }
        try {
            motoRepository.saveAll(motoToSave)
        } catch (ex: Exception) {
            logger.error("Not save moto with urls ${motoToSave.map { it.url }.joinToString(separator = ", ")}")
        }
        return countSave
    }

    fun MotoDto.toMotoEntity(): MotoEntity {
        return MotoEntity(this.lotNumber!!, this.dataAuction, this.year, this.power, this.frameNumber, this.mileage,
                this.grade, this.startPriceJpy, this.startPriceRub, this.endPriceJpy, this.endPriceRub, this.vendorCode,
                this.engineGrade, this.electronicsGrade, this.electronicsGrade, this.appearanceGrade, this.frontGrade,
                this.rearGrade, this.url)
    }
}