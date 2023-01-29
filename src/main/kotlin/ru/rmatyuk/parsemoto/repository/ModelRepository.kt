package ru.rmatyuk.parsemoto.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.ModelEntity

@Repository
interface ModelRepository: CrudRepository<ModelEntity, Long> {

    fun findFirstByName(name: String): ModelEntity?
}