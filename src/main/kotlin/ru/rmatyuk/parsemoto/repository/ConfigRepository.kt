package ru.rmatyuk.parsemoto.repository

import org.springframework.data.repository.CrudRepository
import ru.rmatyuk.parsemoto.entity.ConfigEntity

interface ConfigRepository: CrudRepository<ConfigEntity, String> {
}