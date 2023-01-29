package ru.rmatyuk.parsemoto.service

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.entity.StatusEntity
import ru.rmatyuk.parsemoto.repository.StatusRepository

@Component
class StatusService(statusRepository: StatusRepository) {

    fun save(statusEntity: StatusEntity): StatusEntity {
        var status = statusRepository.findFirstByName(statusEntity.name)
        if (status == null) {
            status = statusRepository.save(statusEntity)
        }
        return status
    }

    private final val statusRepository: StatusRepository

    init {
        this.statusRepository = statusRepository
    }
}