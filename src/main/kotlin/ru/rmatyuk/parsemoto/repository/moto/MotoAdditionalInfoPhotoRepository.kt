package ru.rmatyuk.parsemoto.repository.moto

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.moto.MotoAdditionalInfoPhotoEntity

@Repository
interface MotoAdditionalInfoPhotoRepository: CrudRepository<MotoAdditionalInfoPhotoEntity, Long> {
}