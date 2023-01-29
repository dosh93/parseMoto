package ru.rmatyuk.parsemoto.service.moto

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.rmatyuk.parsemoto.dto.MotoDto
import ru.rmatyuk.parsemoto.entity.AuctionEntity
import ru.rmatyuk.parsemoto.entity.MarkEntity
import ru.rmatyuk.parsemoto.entity.ModelEntity
import ru.rmatyuk.parsemoto.entity.StatusEntity
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import ru.rmatyuk.parsemoto.repository.moto.MotoRepository
import ru.rmatyuk.parsemoto.service.*
import ru.rmatyuk.parsemoto.service.motoOnline.MotoOnlineService

@Component
class MotoService(motoRepository: MotoRepository, motoPhotoService: MotoPhotoService,
                  motoAdditionalInfoService: MotoAdditionalInfoService,
                  motoAdditionalInfoAuctionService: MotoAdditionalInfoAuctionService,
                  commonService: CommonService) {

    private final val motoRepository: MotoRepository
    private final val motoPhotoService: MotoPhotoService
    private final val motoAdditionalInfoService: MotoAdditionalInfoService
    private final val motoAdditionalInfoAuctionService: MotoAdditionalInfoAuctionService
    private final val commonService: CommonService

    var logger: Logger = LoggerFactory.getLogger(MotoService::class.java)

    init {
        this.motoRepository = motoRepository
        this.motoPhotoService = motoPhotoService
        this.motoAdditionalInfoService = motoAdditionalInfoService
        this.motoAdditionalInfoAuctionService = motoAdditionalInfoAuctionService
        this.commonService = commonService
    }

    @Transactional
    fun saveMotoHistory(motos: List<MotoDto>): Int {
        var countSave = 0
        motos.forEach {
            try {
                val moto = motoRepository.findFirstByUrl(it.url!!)
                if (moto == null) {
                    var motoEntity = it.toMotoEntity()
                    val commonFiled = commonService.saveCommonFiled(it.auction!!, it.status!!, it.mark!!, it.model!!)
                    motoEntity.mark = commonFiled.mark
                    motoEntity.model = commonFiled.model
                    motoEntity.auction = commonFiled.auction
                    motoEntity.status = commonFiled.status

                    motoEntity = motoRepository.save(motoEntity)

                    motoPhotoService.saveAll(it.photos, motoEntity)
                    motoAdditionalInfoService.saveAll(it.additionalInfo, motoEntity)
                    motoAdditionalInfoAuctionService.saveAll(it.additionalInfoAuction, motoEntity)
                    countSave++
                    logger.info("Moto save with url ${motoEntity.url}")
                } else {
                    logger.info("Moto exit in DB url ${it.url}")
                }
            } catch (ex: Exception) {
                logger.error("Moto not save with url ${it.url}. ${ex.message}")
            }
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