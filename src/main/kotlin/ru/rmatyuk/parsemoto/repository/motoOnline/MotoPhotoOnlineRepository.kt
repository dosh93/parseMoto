package ru.rmatyuk.parsemoto.repository.motoOnline

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoPhotoOnlineEntity
import java.util.UUID

@Repository
interface MotoPhotoOnlineRepository: CrudRepository<MotoPhotoOnlineEntity, UUID> {

    fun deleteByMoto(moto: MotoOnlineEntity)
}