package ru.rmatyuk.parsemoto.service.moto

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoPhotoDto
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity
import ru.rmatyuk.parsemoto.entity.moto.MotoPhotoEntity
import ru.rmatyuk.parsemoto.repository.moto.MotoPhotoRepository

@Component
class MotoPhotoService(motoPhotoRepository: MotoPhotoRepository) {

    private final val motoPhotoRepository: MotoPhotoRepository

    init {
        this.motoPhotoRepository = motoPhotoRepository
    }

    fun saveAll(photos: MutableList<MotoPhotoDto>, motoEntity: MotoEntity) {
        motoPhotoRepository.saveAll(photos.toMotoPhoto(motoEntity))
    }

    fun MutableList<MotoPhotoDto>.toMotoPhoto(motoEntity: MotoEntity): MutableList<MotoPhotoEntity> {
        return this.map { MotoPhotoEntity(it.urlMain, it.typePhoto, motoEntity) }.toMutableList()
    }
}