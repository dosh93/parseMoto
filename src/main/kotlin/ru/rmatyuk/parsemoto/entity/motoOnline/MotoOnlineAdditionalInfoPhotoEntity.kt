package ru.rmatyuk.parsemoto.entity.motoOnline

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "online_additional_info_photo")
class MotoOnlineAdditionalInfoPhotoEntity(
        val urlMain: String,
        @ManyToOne
        @JoinColumn(name = "additional_info_id")
        val typeAdditionalInfo: MotoOnlineAdditionalInfoEntity,
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
)