package ru.rmatyuk.parsemoto.service.moto

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoAuctionDto
import ru.rmatyuk.parsemoto.entity.moto.MotoAdditionalInfoAuctionEntity
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity
import ru.rmatyuk.parsemoto.repository.moto.MotoAdditionalInfoAuctionRepository

@Component
class MotoAdditionalInfoAuctionService (
        motoAdditionalInfoAuctionRepository: MotoAdditionalInfoAuctionRepository,
        motoAdditionalInfoAuctionValueService: MotoAdditionalInfoAuctionValueService
) {
    private final val motoAdditionalInfoAuctionRepository: MotoAdditionalInfoAuctionRepository
    private final val motoAdditionalInfoAuctionValueService: MotoAdditionalInfoAuctionValueService

    init {
        this.motoAdditionalInfoAuctionRepository = motoAdditionalInfoAuctionRepository
        this.motoAdditionalInfoAuctionValueService = motoAdditionalInfoAuctionValueService
    }

    fun saveAll(additionalInfoAuction: MutableList<MotoAdditionalInfoAuctionDto>, motoEntity: MotoEntity) {
        additionalInfoAuction.forEach {
            val saveItem = motoAdditionalInfoAuctionRepository.save(it.toMotoAdditionalInfoAuction(motoEntity))
            motoAdditionalInfoAuctionValueService.saveAll(it.infoValues, saveItem)
        }
    }

    fun MotoAdditionalInfoAuctionDto.toMotoAdditionalInfoAuction(motoEntity: MotoEntity
    ): MotoAdditionalInfoAuctionEntity {
        return MotoAdditionalInfoAuctionEntity(this.name, this.description, motoEntity)
    }

}