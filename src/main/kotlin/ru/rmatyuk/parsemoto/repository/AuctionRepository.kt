package ru.rmatyuk.parsemoto.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.AuctionEntity

@Repository
interface AuctionRepository: CrudRepository<AuctionEntity, Long> {
    fun findFirstByName(name: String): AuctionEntity?
}