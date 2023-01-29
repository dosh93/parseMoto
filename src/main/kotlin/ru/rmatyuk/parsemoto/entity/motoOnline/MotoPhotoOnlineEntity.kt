package ru.rmatyuk.parsemoto.entity.motoOnline

import jakarta.persistence.*
import ru.rmatyuk.parsemoto.enums.TypePhoto
import java.util.UUID

@Entity
@Table(name = "moto_photo_online")
class MotoPhotoOnlineEntity(
        @Column(columnDefinition = "TEXT")
        val urlMain: String,
        @Enumerated(EnumType.STRING)
        val typePhoto: TypePhoto,
        @ManyToOne
        @JoinColumn(name = "moto_id")
        var moto: MotoOnlineEntity,
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
)