package ru.rmatyuk.parsemoto.repository.motoOnline

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoPhotoEntity
import java.util.UUID

@Repository
interface MotoOnlineAdditionalInfoPhotoRepository: CrudRepository<MotoOnlineAdditionalInfoPhotoEntity, UUID> {
}