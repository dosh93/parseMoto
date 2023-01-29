package ru.rmatyuk.parsemoto.entity.motoOnline

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "online_additional_info_auction")
class MotoOnlineAdditionalInfoAuctionEntity(
        val name: String,
        @Column(columnDefinition = "TEXT")
        val description: String,
        @ManyToOne
        @JoinColumn(name = "moto_id")
        var moto: MotoOnlineEntity,
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
        @OneToMany(mappedBy = "additionalInfoAuction", cascade = [CascadeType.REMOVE])
        val infoValues: MutableList<MotoOnlineAdditionalInfoAuctionValueEntity> = mutableListOf(),
)