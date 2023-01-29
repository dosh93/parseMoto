package ru.rmatyuk.parsemoto.entity.moto

import jakarta.persistence.*

@Entity
@Table(name = "additional_info_auction_value")
class MotoAdditionalInfoAuctionValueEntity(
        val name: String,
        @Column(columnDefinition = "TEXT")
        val description: String,
        @ManyToOne
        @JoinColumn(name = "additional_info_auction_id")
        val additionalInfoAuction: MotoAdditionalInfoAuctionEntity,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
)