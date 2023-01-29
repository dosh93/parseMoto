package ru.rmatyuk.parsemoto.dto

class MotoAdditionalInfoDto(
        val name: String,
        val grade: Int,
        val infoValues: MutableList<MotoAdditionalInfoValueDto>,
        val photos: MutableList<MotoAdditionalInfoPhotoDto>,
)