package ru.rmatyuk.parsemoto.service.motoOnline

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoValueDto
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoValueEntity
import ru.rmatyuk.parsemoto.repository.motoOnline.MotoOnlineAdditionalInfoValueRepository

@Component
class MotoOnlineAdditionalInfoValueService(motoOnlineAdditionalInfoValueRepository: MotoOnlineAdditionalInfoValueRepository) {
    private final val motoOnlineAdditionalInfoValueRepository: MotoOnlineAdditionalInfoValueRepository

    init {
        this.motoOnlineAdditionalInfoValueRepository = motoOnlineAdditionalInfoValueRepository
    }

    fun saveAll(
            infoValues: MutableList<MotoAdditionalInfoValueDto>,
            motoOnlineAdditionalInfoEntity: MotoOnlineAdditionalInfoEntity
    ) {
        motoOnlineAdditionalInfoValueRepository
                .saveAll(infoValues.toMotoOnlineAdditionalInfoValue(motoOnlineAdditionalInfoEntity))
    }

    fun MutableList<MotoAdditionalInfoValueDto>.toMotoOnlineAdditionalInfoValue(
            motoOnlineAdditionalInfoEntity: MotoOnlineAdditionalInfoEntity
    ): MutableList<MotoOnlineAdditionalInfoValueEntity> {
        return this.map { MotoOnlineAdditionalInfoValueEntity(it.name, it.description, motoOnlineAdditionalInfoEntity) }
                .toMutableList()
    }
}