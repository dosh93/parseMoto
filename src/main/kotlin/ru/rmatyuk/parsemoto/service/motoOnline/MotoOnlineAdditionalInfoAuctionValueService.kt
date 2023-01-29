package ru.rmatyuk.parsemoto.service.motoOnline

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoAuctionValueDto
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoAuctionEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoAuctionValueEntity
import ru.rmatyuk.parsemoto.repository.motoOnline.MotoOnlineAdditionalInfoAuctionValueRepository

@Component
class MotoOnlineAdditionalInfoAuctionValueService(
        motoOnlineAdditionalInfoAuctionValueRepository: MotoOnlineAdditionalInfoAuctionValueRepository,
) {
    private final val motoOnlineAdditionalInfoAuctionValueRepository: MotoOnlineAdditionalInfoAuctionValueRepository

    init {
        this.motoOnlineAdditionalInfoAuctionValueRepository = motoOnlineAdditionalInfoAuctionValueRepository
    }

    fun saveAll(
            infoValues: MutableList<MotoAdditionalInfoAuctionValueDto>,
            motoOnlineAdditionalInfoAuctionEntity: MotoOnlineAdditionalInfoAuctionEntity
    ) {
        motoOnlineAdditionalInfoAuctionValueRepository
                .saveAll(infoValues.toMotoAdditionalInfoAuctionValue(motoOnlineAdditionalInfoAuctionEntity))
    }

    fun MutableList<MotoAdditionalInfoAuctionValueDto>.toMotoAdditionalInfoAuctionValue(
            motoOnlineAdditionalInfoAuctionEntity: MotoOnlineAdditionalInfoAuctionEntity
    ): MutableList<MotoOnlineAdditionalInfoAuctionValueEntity> {
        return this.map { MotoOnlineAdditionalInfoAuctionValueEntity(it.name, it.description, motoOnlineAdditionalInfoAuctionEntity) }.toMutableList()
    }
}