package ru.rmatyuk.parsemoto.entity.moto

import jakarta.persistence.*
import ru.rmatyuk.parsemoto.enums.TypePhoto

@Entity
@Table(name = "moto_photo")
class MotoPhotoEntity(
        @Column(columnDefinition = "TEXT")
        val urlMain: String,
        @Enumerated(EnumType.STRING)
        val typePhoto: TypePhoto,
        @ManyToOne
        @JoinColumn(name = "moto_id")
        var moto: MotoEntity,
        @Column(columnDefinition = "TEXT")
        val urlLocal: String? = null,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
)
