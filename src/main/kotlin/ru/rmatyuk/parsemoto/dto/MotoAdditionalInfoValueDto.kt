package ru.rmatyuk.parsemoto.dto

import kotlinx.serialization.Serializable

@Serializable
class MotoAdditionalInfoValueDto(
        val name: String,
        val description: String,
)