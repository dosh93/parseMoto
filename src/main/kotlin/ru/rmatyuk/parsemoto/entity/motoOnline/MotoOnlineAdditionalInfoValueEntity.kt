package ru.rmatyuk.parsemoto.entity.motoOnline

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "online_additional_info_value")
class MotoOnlineAdditionalInfoValueEntity(
        val name: String,
        @Column(columnDefinition = "TEXT")
        val description: String,
        @ManyToOne
        @JoinColumn(name = "additional_info_id")
        val typeAdditionalInfo: MotoOnlineAdditionalInfoEntity,
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
)