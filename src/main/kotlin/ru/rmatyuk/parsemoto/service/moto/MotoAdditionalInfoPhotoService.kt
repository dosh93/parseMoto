package ru.rmatyuk.parsemoto.service.moto

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoPhotoDto
import ru.rmatyuk.parsemoto.entity.moto.MotoAdditionalInfoEntity
import ru.rmatyuk.parsemoto.entity.moto.MotoAdditionalInfoPhotoEntity
import ru.rmatyuk.parsemoto.repository.moto.MotoAdditionalInfoPhotoRepository

@Component
class MotoAdditionalInfoPhotoService(motoAdditionalInfoPhotoRepository: MotoAdditionalInfoPhotoRepository) {
    private final val motoAdditionalInfoPhotoRepository: MotoAdditionalInfoPhotoRepository

    init {
        this.motoAdditionalInfoPhotoRepository = motoAdditionalInfoPhotoRepository
    }

    fun saveAll(
            photos: MutableList<MotoAdditionalInfoPhotoDto>,
            motoAdditionalInfoEntity: MotoAdditionalInfoEntity
    ) {
        motoAdditionalInfoPhotoRepository.saveAll(photos.toMotoAdditionalInfoPhoto(motoAdditionalInfoEntity))
    }

    fun MutableList<MotoAdditionalInfoPhotoDto>.toMotoAdditionalInfoPhoto(
            motoAdditionalInfoEntity: MotoAdditionalInfoEntity
    ): MutableList<MotoAdditionalInfoPhotoEntity> {
        return this.map { MotoAdditionalInfoPhotoEntity(it.urlMain, motoAdditionalInfoEntity) }.toMutableList()
    }
}