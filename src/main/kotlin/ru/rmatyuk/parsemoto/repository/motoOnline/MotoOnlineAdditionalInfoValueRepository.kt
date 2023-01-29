package ru.rmatyuk.parsemoto.repository.motoOnline

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoValueEntity
import java.util.UUID

@Repository
interface MotoOnlineAdditionalInfoValueRepository: CrudRepository<MotoOnlineAdditionalInfoValueEntity, UUID> {
}