package ru.rmatyuk.parsemoto.entity.motoOnline

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "online_additional_info")
class MotoOnlineAdditionalInfoEntity(
        val name: String,
        val grade: Int,
        @ManyToOne
        @JoinColumn(name = "moto_id")
        var moto: MotoOnlineEntity,
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
        @OneToMany(mappedBy = "typeAdditionalInfo", cascade = [CascadeType.REMOVE])
        val infoValues: MutableList<MotoOnlineAdditionalInfoValueEntity> = emptyList<MotoOnlineAdditionalInfoValueEntity>().toMutableList(),
        @OneToMany(mappedBy = "typeAdditionalInfo", cascade = [CascadeType.REMOVE])
        val photos: MutableList<MotoOnlineAdditionalInfoPhotoEntity> = emptyList<MotoOnlineAdditionalInfoPhotoEntity>().toMutableList(),
)