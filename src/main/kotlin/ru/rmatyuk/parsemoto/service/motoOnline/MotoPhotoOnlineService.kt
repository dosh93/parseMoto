package ru.rmatyuk.parsemoto.service.motoOnline

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoPhotoDto
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoPhotoOnlineEntity
import ru.rmatyuk.parsemoto.repository.motoOnline.MotoPhotoOnlineRepository

@Component
class MotoPhotoOnlineService(motoPhotoOnlineRepository: MotoPhotoOnlineRepository) {

    private final val motoPhotoOnlineRepository: MotoPhotoOnlineRepository

    init {
        this.motoPhotoOnlineRepository = motoPhotoOnlineRepository
    }

    fun deleteAllByMoto(moto: MotoOnlineEntity) {
        motoPhotoOnlineRepository.deleteByMoto(moto)
    }

    fun saveAll(photos: MutableList<MotoPhotoDto>, motoEntity: MotoOnlineEntity) {
        motoPhotoOnlineRepository.saveAll(photos.toMotoPhotoOnline(motoEntity))
    }

    fun MutableList<MotoPhotoDto>.toMotoPhotoOnline(motoEntity: MotoOnlineEntity): MutableList<MotoPhotoOnlineEntity> {
        return this.map { MotoPhotoOnlineEntity(it.urlMain, it.typePhoto, motoEntity) }.toMutableList()
    }

    fun deleteAll(photos: MutableList<MotoPhotoOnlineEntity>) {
        motoPhotoOnlineRepository.deleteAll(photos)
    }

}