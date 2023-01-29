package ru.rmatyuk.parsemoto.service

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.entity.MarkEntity
import ru.rmatyuk.parsemoto.repository.MarkRepository

@Component
class MarkService(markRepository: MarkRepository) {

    private final val markRepository: MarkRepository

    init {
        this.markRepository = markRepository
    }

    fun save(markEntity: MarkEntity): MarkEntity {
        var mark = markRepository.findFirstByName(markEntity.name)
        if (mark == null) {
            mark = markRepository.save(markEntity)
        }
        return mark
    }
}