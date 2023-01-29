package ru.rmatyuk.parsemoto.service

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.entity.AuctionEntity
import ru.rmatyuk.parsemoto.repository.AuctionRepository

@Component
class AuctionService(auctionRepository: AuctionRepository) {

    fun save(auctionEntity: AuctionEntity): AuctionEntity {
        var auction = auctionRepository.findFirstByName(auctionEntity.name)
        if (auction == null) {
            auction = auctionRepository.save(auctionEntity)
        }
        return auction
    }

    private final val auctionRepository: AuctionRepository

    init {
        this.auctionRepository = auctionRepository
    }
}