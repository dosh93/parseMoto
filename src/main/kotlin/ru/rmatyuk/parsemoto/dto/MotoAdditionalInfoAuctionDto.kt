package ru.rmatyuk.parsemoto.dto

import kotlinx.serialization.Serializable

@Serializable
class MotoAdditionalInfoAuctionDto(
        val name: String,
        val description: String,
        val infoValues: MutableList<MotoAdditionalInfoAuctionValueDto>,
)