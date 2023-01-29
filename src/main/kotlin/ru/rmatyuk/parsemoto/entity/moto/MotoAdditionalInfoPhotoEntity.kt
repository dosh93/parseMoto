package ru.rmatyuk.parsemoto.entity.moto

import jakarta.persistence.*

@Entity
@Table(name = "additional_info_photo")
class MotoAdditionalInfoPhotoEntity(
        val urlMain: String,
        @ManyToOne
        @JoinColumn(name = "additional_info_id")
        val typeAdditionalInfo: MotoAdditionalInfoEntity,
        val urlLocal: String? = null,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
)