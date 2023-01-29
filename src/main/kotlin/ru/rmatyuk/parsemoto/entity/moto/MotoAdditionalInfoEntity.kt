package ru.rmatyuk.parsemoto.entity.moto

import jakarta.persistence.*

@Entity
@Table(name = "additional_info")
class MotoAdditionalInfoEntity(
        val name: String,
        val grade: Int,
        @ManyToOne
        @JoinColumn(name = "moto_id")
        var moto: MotoEntity,
        @OneToMany(mappedBy = "typeAdditionalInfo")
        val infoValues: MutableList<MotoAdditionalInfoValueEntity> = mutableListOf(),
        @OneToMany(mappedBy = "typeAdditionalInfo")
        val photos: MutableList<MotoAdditionalInfoPhotoEntity> = mutableListOf(),
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
)