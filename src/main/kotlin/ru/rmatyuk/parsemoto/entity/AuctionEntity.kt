package ru.rmatyuk.parsemoto.entity

import jakarta.persistence.*
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import java.util.Collections.emptyList
@kotlinx.serialization.Serializable
@Entity
@Table(name = "auction")
class AuctionEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val name: String,
        @OneToMany(mappedBy = "auction")
        val motos: MutableList<MotoEntity> = emptyList(),
        @OneToMany(mappedBy = "auction")
        val motosOnline: MutableList<MotoOnlineEntity> = emptyList()

)