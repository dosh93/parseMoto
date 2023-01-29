package ru.rmatyuk.parsemoto.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.StatusEntity

@Repository
interface StatusRepository: CrudRepository<StatusEntity, Long> {
    fun findFirstByName(name: String): StatusEntity?
}