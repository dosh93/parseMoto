package ru.rmatyuk.parsemoto.entity.moto

import jakarta.persistence.*
import ru.rmatyuk.parsemoto.entity.AuctionEntity
import ru.rmatyuk.parsemoto.entity.MarkEntity
import ru.rmatyuk.parsemoto.entity.ModelEntity
import ru.rmatyuk.parsemoto.entity.StatusEntity
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "moto")
class MotoEntity(
        var lotNumber: String = "",
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
        var vendorCode: String? = null,
        var engineGrade: Int? = null,
        var electronicsGrade: Int? = null,
        var frameGrade: Int? = null,
        var appearanceGrade: Int? = null,
        var frontGrade: Int? = null,
        var rearGrade: Int? = null,
        var url: String? = null,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @ManyToOne
        @JoinColumn(name = "auction_id")
        var auction: AuctionEntity? = null,
        @ManyToOne
        @JoinColumn(name = "mark_id")
        var mark: MarkEntity? = null,
        @ManyToOne
        @JoinColumn(name = "model_id")
        var model: ModelEntity? = null,
        @ManyToOne
        @JoinColumn(name = "status_id")
        var status: StatusEntity? = null,
        @Column(columnDefinition = "TEXT")
        var additionalInfoJson: String? = null,
        @Column(columnDefinition = "TEXT")
        var additionalInfoAuctionJson: String? = null,
        @Column(columnDefinition = "TEXT")
        var photosMainJson: String? = null,
        @Column(columnDefinition = "TEXT")
        var photosPreview: String? = null,
)