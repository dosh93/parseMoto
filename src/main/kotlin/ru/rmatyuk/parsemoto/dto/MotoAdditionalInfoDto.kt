package ru.rmatyuk.parsemoto.dto

import kotlinx.serialization.Serializable

@Serializable
class MotoAdditionalInfoDto(
        val name: String,
        val grade: Int,
        val infoValues: MutableList<MotoAdditionalInfoValueDto>,
        val photos: MutableList<MotoAdditionalInfoPhotoDto>,
)