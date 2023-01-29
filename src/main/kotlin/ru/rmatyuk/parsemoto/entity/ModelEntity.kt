package ru.rmatyuk.parsemoto.entity

import jakarta.persistence.*
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import java.util.Collections.emptyList
@Entity
@Table(name = "model")
class ModelEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        var name: String,
        @OneToMany(mappedBy = "model")
        var motos: MutableList<MotoEntity> = emptyList(),
        @OneToMany(mappedBy = "model")
        var motosOnline: MutableList<MotoOnlineEntity> = emptyList(),
        @ManyToOne
        @JoinColumn(name = "mark_id")
        var mark: MarkEntity? = null,

        )