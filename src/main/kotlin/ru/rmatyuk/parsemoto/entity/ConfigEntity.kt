package ru.rmatyuk.parsemoto.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "config_app")
class ConfigEntity(
        @Id
        var configName: String,
        val description: String,
        var configValue: String? = null
)