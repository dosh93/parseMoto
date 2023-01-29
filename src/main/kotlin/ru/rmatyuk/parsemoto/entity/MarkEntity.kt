package ru.rmatyuk.parsemoto.entity

import jakarta.persistence.*
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import java.util.Collections.emptyList
@kotlinx.serialization.Serializable
@Entity
@Table(name = "mark")
class MarkEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val name: String,
        @OneToMany(mappedBy = "mark")
        val motos: MutableList<MotoEntity> = emptyList(),
        @OneToMany(mappedBy = "mark")
        val motosOnline: MutableList<MotoOnlineEntity> = emptyList(),
        @OneToMany(mappedBy = "mark")
        val models: MutableList<ModelEntity> = emptyList()
)