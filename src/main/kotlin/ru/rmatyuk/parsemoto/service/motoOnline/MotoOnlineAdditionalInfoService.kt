package ru.rmatyuk.parsemoto.service.motoOnline

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoDto
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineAdditionalInfoEntity
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import ru.rmatyuk.parsemoto.repository.motoOnline.MotoOnlineAdditionalInfoRepository

@Component
class MotoOnlineAdditionalInfoService(motoOnlineAdditionalInfoRepository: MotoOnlineAdditionalInfoRepository,
                                      motoOnlineAdditionalInfoValueService: MotoOnlineAdditionalInfoValueService,
                                      motoOnlineAdditionalInfoPhotoService: MotoOnlineAdditionalInfoPhotoService) {
    private final val motoOnlineAdditionalInfoRepository: MotoOnlineAdditionalInfoRepository
    private final val motoOnlineAdditionalInfoValueService: MotoOnlineAdditionalInfoValueService
    private final val motoOnlineAdditionalInfoPhotoService: MotoOnlineAdditionalInfoPhotoService

    init {
        this.motoOnlineAdditionalInfoRepository = motoOnlineAdditionalInfoRepository
        this.motoOnlineAdditionalInfoValueService = motoOnlineAdditionalInfoValueService
        this.motoOnlineAdditionalInfoPhotoService = motoOnlineAdditionalInfoPhotoService
    }

    fun deleteAllByMoto(motoEntity: MotoOnlineEntity) {
        motoOnlineAdditionalInfoRepository.deleteByMoto(motoEntity)
    }

    fun saveAll(additionalInfo: MutableList<MotoAdditionalInfoDto>, motoEntity: MotoOnlineEntity) {
        additionalInfo.forEach {
            val saveItem = motoOnlineAdditionalInfoRepository.save(it.toMotoOnlineAdditionalInfo(motoEntity))
            motoOnlineAdditionalInfoValueService.saveAll(it.infoValues, saveItem)
            motoOnlineAdditionalInfoPhotoService.saveAll(it.photos, saveItem)
        }
    }

    fun MotoAdditionalInfoDto.toMotoOnlineAdditionalInfo(motoEntity: MotoOnlineEntity): MotoOnlineAdditionalInfoEntity {
        return MotoOnlineAdditionalInfoEntity(this.name, this.grade, motoEntity)
    }

    fun deleteAll(additionalInfo: MutableList<MotoOnlineAdditionalInfoEntity>) {
        motoOnlineAdditionalInfoRepository.deleteAll(additionalInfo)
    }
}