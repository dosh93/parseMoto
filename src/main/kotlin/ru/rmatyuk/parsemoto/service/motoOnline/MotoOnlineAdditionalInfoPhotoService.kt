package ru.rmatyuk.parsemoto.service.motoOnline

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoPhotoDto
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoPhotoEntity
import ru.rmatyuk.parsemoto.repository.motoOnline.MotoOnlineAdditionalInfoPhotoRepository

@Component
class MotoOnlineAdditionalInfoPhotoService(motoOnlineAdditionalInfoPhotoRepository: MotoOnlineAdditionalInfoPhotoRepository) {
    private final val motoOnlineAdditionalInfoPhotoRepository: MotoOnlineAdditionalInfoPhotoRepository

    init {
        this.motoOnlineAdditionalInfoPhotoRepository = motoOnlineAdditionalInfoPhotoRepository
    }

    fun saveAll(
            photos: MutableList<MotoAdditionalInfoPhotoDto>,
            motoOnlineAdditionalInfoEntity: MotoOnlineAdditionalInfoEntity
    ) {
        motoOnlineAdditionalInfoPhotoRepository.saveAll(photos.toMotoOnlineAdditionalInfoPhoto(motoOnlineAdditionalInfoEntity))
    }

    fun MutableList<MotoAdditionalInfoPhotoDto>.toMotoOnlineAdditionalInfoPhoto(
            motoOnlineAdditionalInfoEntity: MotoOnlineAdditionalInfoEntity
    ): MutableList<MotoOnlineAdditionalInfoPhotoEntity> {
        return this.map { MotoOnlineAdditionalInfoPhotoEntity(it.urlMain, motoOnlineAdditionalInfoEntity) }.toMutableList()
    }
}


