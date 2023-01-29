package ru.rmatyuk.parsemoto.entity

import jakarta.persistence.*
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity
import java.util.Collections.emptyList
@kotlinx.serialization.Serializable
@Entity
@Table(name = "status")
class StatusEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val name: String,
        @OneToMany(mappedBy = "status")
        val motos: MutableList<MotoEntity> = emptyList()
)