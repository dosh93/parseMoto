package ru.rmatyuk.parsemoto.entity.motoOnline

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "online_additional_info_auction_value")
class MotoOnlineAdditionalInfoAuctionValueEntity(
        val name: String,
        @Column(columnDefinition = "TEXT")
        val description: String,
        @ManyToOne
        @JoinColumn(name = "additional_info_auction_id")
        val additionalInfoAuction: MotoOnlineAdditionalInfoAuctionEntity,
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
)