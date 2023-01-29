package ru.rmatyuk.parsemoto.dto

import java.time.LocalDate
import java.util.*
import kotlinx.serialization.*

@Serializable
class MotoDto (
        var lotNumber: String? = "",
        var mark: String? = "",
        var model: String? = "",
        var url: String? = "",
        var new: Boolean? = null,
        var photos: MutableList<MotoPhotoDto> = Collections.emptyList(),
        var auction: String? = null,
        @Contextual
        var dataAuction: LocalDate? = null,
        var year: Int? = null,
        var power: Int? = null,
        var frameNumber: String? = null,
        var mileage: Long? = null,
        var grade: Double? = null,
        var startPriceJpy: Long? = null,
        var startPriceRub: Long? = null,
        var endPriceJpy: Long? = null,
        var endPriceRub: Long? = null,
        var status: String? = null,
        var vendorCode: String? = null,
        var engineGrade: Int? = null,
        var electronicsGrade: Int? = null,
        var frameGrade: Int? = null,
        var appearanceGrade: Int? = null,
        var frontGrade: Int? = null,
        var rearGrade: Int? = null,
        var additionalInfo: MutableList<MotoAdditionalInfoDto> = Collections.emptyList(),
        var additionalInfoAuction: MutableList<MotoAdditionalInfoAuctionDto> = Collections.emptyList(),
)