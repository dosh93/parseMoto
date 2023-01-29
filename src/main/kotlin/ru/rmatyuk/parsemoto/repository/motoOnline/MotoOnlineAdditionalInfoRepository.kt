package ru.rmatyuk.parsemoto.repository.motoOnline

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import java.util.UUID

@Repository
interface MotoOnlineAdditionalInfoRepository: CrudRepository<MotoOnlineAdditionalInfoEntity, UUID> {
    fun deleteByMoto(motoEntity: MotoOnlineEntity)
}