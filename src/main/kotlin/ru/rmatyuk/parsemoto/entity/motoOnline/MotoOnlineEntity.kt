package ru.rmatyuk.parsemoto.entity.motoOnline

import jakarta.persistence.*
import ru.rmatyuk.parsemoto.entity.AuctionEntity
import ru.rmatyuk.parsemoto.entity.MarkEntity
import ru.rmatyuk.parsemoto.entity.ModelEntity
import ru.rmatyuk.parsemoto.entity.StatusEntity
import java.time.LocalDate
import java.util.*


@Entity
@Table(name = "moto_online")
class MotoOnlineEntity(
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
        var new: Boolean? = null,
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
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
        @OneToMany(mappedBy = "moto", cascade = [CascadeType.REMOVE])
        var photos: MutableList<MotoPhotoOnlineEntity> = Collections.emptyList(),
        @OneToMany(mappedBy = "moto", cascade = [CascadeType.REMOVE])
        var additionalInfo: MutableList<MotoOnlineAdditionalInfoEntity> = Collections.emptyList(),
        @OneToMany(mappedBy = "moto", cascade = [CascadeType.REMOVE])
        var additionalInfoAuction: MutableList<MotoOnlineAdditionalInfoAuctionEntity> = Collections.emptyList(),
)
