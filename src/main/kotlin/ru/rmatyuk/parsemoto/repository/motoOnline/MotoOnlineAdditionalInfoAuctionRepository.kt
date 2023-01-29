package ru.rmatyuk.parsemoto.repository.motoOnline

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoAuctionEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import java.util.UUID

@Repository
interface MotoOnlineAdditionalInfoAuctionRepository: CrudRepository<MotoOnlineAdditionalInfoAuctionEntity, UUID> {
    fun deleteByMoto(motoEntity: MotoOnlineEntity)
}