package ru.rmatyuk.parsemoto.entity.moto

import jakarta.persistence.*

@Entity
@Table(name = "additional_info_auction")
class MotoAdditionalInfoAuctionEntity(
        val name: String,
        @Column(columnDefinition = "TEXT")
        val description: String,
        @ManyToOne
        @JoinColumn(name = "moto_id")
        var moto: MotoEntity,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @OneToMany(mappedBy = "additionalInfoAuction")
        val infoValues: MutableList<MotoAdditionalInfoAuctionValueEntity> = mutableListOf(),
)