package ru.rmatyuk.parsemoto.service.moto

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoAuctionValueDto
import ru.rmatyuk.parsemoto.entity.moto.MotoAdditionalInfoAuctionEntity
import ru.rmatyuk.parsemoto.entity.moto.MotoAdditionalInfoAuctionValueEntity
import ru.rmatyuk.parsemoto.repository.moto.MotoAdditionalInfoAuctionValueRepository

@Component
class MotoAdditionalInfoAuctionValueService (
        motoAdditionalInfoAuctionValueRepository: MotoAdditionalInfoAuctionValueRepository,
) {
    private final val motoAdditionalInfoAuctionValueRepository: MotoAdditionalInfoAuctionValueRepository

    init {
        this.motoAdditionalInfoAuctionValueRepository = motoAdditionalInfoAuctionValueRepository
    }

    fun saveAll(
            infoValues: MutableList<MotoAdditionalInfoAuctionValueDto>,
            motoAdditionalInfoAuctionEntity: MotoAdditionalInfoAuctionEntity
    ) {
        motoAdditionalInfoAuctionValueRepository
                .saveAll(infoValues.toMotoAdditionalInfoAuctionValue(motoAdditionalInfoAuctionEntity))
    }

    fun MutableList<MotoAdditionalInfoAuctionValueDto>.toMotoAdditionalInfoAuctionValue(
            motoAdditionalInfoAuctionEntity: MotoAdditionalInfoAuctionEntity
    ): MutableList<MotoAdditionalInfoAuctionValueEntity> {
        return this.map { MotoAdditionalInfoAuctionValueEntity(it.name, it.description, motoAdditionalInfoAuctionEntity) }.toMutableList()
    }
}