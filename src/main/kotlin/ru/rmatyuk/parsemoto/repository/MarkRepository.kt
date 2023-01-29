package ru.rmatyuk.parsemoto.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.MarkEntity

@Repository
interface MarkRepository: CrudRepository<MarkEntity, Long> {
    fun findFirstByName(name: String): MarkEntity?
}