package ru.rmatyuk.parsemoto.service.moto

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.MotoAdditionalInfoDto
import ru.rmatyuk.parsemoto.entity.moto.MotoAdditionalInfoEntity
import ru.rmatyuk.parsemoto.entity.moto.MotoEntity
import ru.rmatyuk.parsemoto.repository.moto.MotoAdditionalInfoRepository

@Component
class MotoAdditionalInfoService(motoAdditionalInfoRepository: MotoAdditionalInfoRepository,
                                motoAdditionalInfoValueService: MotoAdditionalInfoValueService,
                                motoAdditionalInfoPhotoService: MotoAdditionalInfoPhotoService) {
    private final val motoAdditionalInfoRepository: MotoAdditionalInfoRepository
    private final val motoAdditionalInfoValueService: MotoAdditionalInfoValueService
    private final val motoAdditionalInfoPhotoService: MotoAdditionalInfoPhotoService

    init {
        this.motoAdditionalInfoRepository = motoAdditionalInfoRepository
        this.motoAdditionalInfoValueService = motoAdditionalInfoValueService
        this.motoAdditionalInfoPhotoService = motoAdditionalInfoPhotoService
    }

    fun saveAll(additionalInfo: MutableList<MotoAdditionalInfoDto>, motoEntity: MotoEntity) {
        additionalInfo.forEach {
            val saveItem = motoAdditionalInfoRepository.save(it.toMotoAdditionalInfo(motoEntity))
            motoAdditionalInfoValueService.saveAll(it.infoValues, saveItem)
            motoAdditionalInfoPhotoService.saveAll(it.photos, saveItem)
        }
    }

    fun MotoAdditionalInfoDto.toMotoAdditionalInfo(motoEntity: MotoEntity): MotoAdditionalInfoEntity {
        return MotoAdditionalInfoEntity(this.name, this.grade, motoEntity)
    }
}