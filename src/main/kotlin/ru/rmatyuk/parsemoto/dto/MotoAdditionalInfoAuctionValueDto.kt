package ru.rmatyuk.parsemoto.dto

import kotlinx.serialization.Serializable

@Serializable
class MotoAdditionalInfoAuctionValueDto(
        val name: String,
        val description: String
)