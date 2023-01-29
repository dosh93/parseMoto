package ru.rmatyuk.parsemoto.service.moto

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoValueDto
import ru.rmatyuk.parsemoto.entity.moto.MotoAdditionalInfoEntity
import ru.rmatyuk.parsemoto.entity.moto.MotoAdditionalInfoValueEntity
import ru.rmatyuk.parsemoto.repository.moto.MotoAdditionalInfoValueRepository

@Component
class MotoAdditionalInfoValueService(motoAdditionalInfoValueRepository: MotoAdditionalInfoValueRepository) {
    private final val motoAdditionalInfoValueRepository: MotoAdditionalInfoValueRepository

    init {
        this.motoAdditionalInfoValueRepository = motoAdditionalInfoValueRepository
    }

    fun saveAll(
            infoValues: MutableList<MotoAdditionalInfoValueDto>,
            motoAdditionalInfoEntity: MotoAdditionalInfoEntity
    ) {
        motoAdditionalInfoValueRepository
                .saveAll(infoValues.toMotoAdditionalInfoValue(motoAdditionalInfoEntity))
    }

    fun MutableList<MotoAdditionalInfoValueDto>.toMotoAdditionalInfoValue(
            motoAdditionalInfoEntity: MotoAdditionalInfoEntity
    ): MutableList<MotoAdditionalInfoValueEntity> {
        return this.map { MotoAdditionalInfoValueEntity(it.name, it.description, motoAdditionalInfoEntity) }
                .toMutableList()
    }
}