package ru.rmatyuk.parsemoto.service.motoOnline

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoAuctionDto
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoAuctionEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import ru.rmatyuk.parsemoto.repository.motoOnline.MotoOnlineAdditionalInfoAuctionRepository

@Component
class MotoOnlineAdditionalInfoAuctionService(
        motoOnlineAdditionalInfoAuctionRepository: MotoOnlineAdditionalInfoAuctionRepository,
        motoOnlineAdditionalInfoAuctionValueService: MotoOnlineAdditionalInfoAuctionValueService
) {
    private final val motoOnlineAdditionalInfoAuctionRepository: MotoOnlineAdditionalInfoAuctionRepository
    private final val motoOnlineAdditionalInfoAuctionValueService: MotoOnlineAdditionalInfoAuctionValueService

    init {
        this.motoOnlineAdditionalInfoAuctionRepository = motoOnlineAdditionalInfoAuctionRepository
        this.motoOnlineAdditionalInfoAuctionValueService = motoOnlineAdditionalInfoAuctionValueService
    }

    fun deleteAllByMoto(motoEntity: MotoOnlineEntity) {
        motoOnlineAdditionalInfoAuctionRepository.deleteByMoto(motoEntity)
    }

    fun saveAll(additionalInfoAuction: MutableList<MotoAdditionalInfoAuctionDto>, motoEntity: MotoOnlineEntity) {
        additionalInfoAuction.forEach {
            val saveItem = motoOnlineAdditionalInfoAuctionRepository.save(it.toMotoOnlineAdditionalInfoAuction(motoEntity))
            motoOnlineAdditionalInfoAuctionValueService.saveAll(it.infoValues, saveItem)
        }
    }

    fun MotoAdditionalInfoAuctionDto.toMotoOnlineAdditionalInfoAuction(motoEntity: MotoOnlineEntity
    ): MotoOnlineAdditionalInfoAuctionEntity {
        return MotoOnlineAdditionalInfoAuctionEntity(this.name, this.description, motoEntity)
    }

    fun deleteAll(additionalInfoAuction: MutableList<MotoOnlineAdditionalInfoAuctionEntity>) {
        motoOnlineAdditionalInfoAuctionRepository.deleteAll(additionalInfoAuction)
    }
}