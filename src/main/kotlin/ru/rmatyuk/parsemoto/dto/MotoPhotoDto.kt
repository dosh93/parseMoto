package ru.rmatyuk.parsemoto.dto

import ru.rmatyuk.parsemoto.enums.TypePhoto
import kotlinx.serialization.*
@Serializable
class MotoPhotoDto (
        val urlMain: String,
        val typePhoto: TypePhoto,
)