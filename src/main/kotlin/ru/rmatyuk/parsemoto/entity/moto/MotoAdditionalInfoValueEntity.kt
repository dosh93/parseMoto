package ru.rmatyuk.parsemoto.entity.moto

import jakarta.persistence.*

@Entity
@Table(name = "additional_info_value")
class MotoAdditionalInfoValueEntity(
        val name: String,
        @Column(columnDefinition = "TEXT")
        val description: String,
        @ManyToOne
        @JoinColumn(name = "additional_info_id")
        val typeAdditionalInfo: MotoAdditionalInfoEntity,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
)