package ru.rmatyuk.parsemoto.dto

class MotoAdditionalInfoAuctionDto(
        val name: String,
        val description: String,
        val infoValues: MutableList<MotoAdditionalInfoAuctionValueDto>,
)