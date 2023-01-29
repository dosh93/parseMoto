package ru.rmatyuk.parsemoto.service

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.entity.ModelEntity
import ru.rmatyuk.parsemoto.repository.ModelRepository

@Component
class ModelService(modelRepository: ModelRepository) {

    private final val modelRepository: ModelRepository

    init {
        this.modelRepository = modelRepository
    }

    fun save(modelEntity: ModelEntity): ModelEntity {
        var model = modelRepository.findFirstByName(modelEntity.name)
        if (model == null) {
            model = modelRepository.save(modelEntity)
        }
        return model
    }
}