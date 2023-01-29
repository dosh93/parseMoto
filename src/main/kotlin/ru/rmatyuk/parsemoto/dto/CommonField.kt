package ru.rmatyuk.parsemoto.dto

import ru.rmatyuk.parsemoto.entity.AuctionEntity
import ru.rmatyuk.parsemoto.entity.MarkEntity
import ru.rmatyuk.parsemoto.entity.ModelEntity
import ru.rmatyuk.parsemoto.entity.StatusEntity

data class CommonField(
        val auction: AuctionEntity,
        val status: StatusEntity,
        val mark: MarkEntity,
        val model: ModelEntity
)
