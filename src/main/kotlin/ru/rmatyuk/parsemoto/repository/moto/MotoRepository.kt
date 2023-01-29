package ru.rmatyuk.parsemoto.repository.moto

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity

@Repository
interface MotoRepository: CrudRepository<MotoEntity, Long> {

    fun findFirstByUrl(url: String) : MotoEntity?
}