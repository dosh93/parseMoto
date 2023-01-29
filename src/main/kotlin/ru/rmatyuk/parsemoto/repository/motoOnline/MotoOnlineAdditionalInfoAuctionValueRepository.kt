package ru.rmatyuk.parsemoto.repository.motoOnline

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoAuctionValueEntity
import java.util.UUID

@Repository
interface MotoOnlineAdditionalInfoAuctionValueRepository: CrudRepository<MotoOnlineAdditionalInfoAuctionValueEntity, UUID> {
}