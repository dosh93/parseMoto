package ru.rmatyuk.parsemoto.repository.motoOnline

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.rmatyuk.parsemoto.entity.motoOnline.MotoOnlineEntity
import java.util.*

@Repository
interface MotoOnlineRepository : CrudRepository<MotoOnlineEntity, UUID> {
    fun findFirstByUrl(url: String) : MotoOnlineEntity?
    fun deleteByUrl(url: String)

    @Modifying
    @Query("update MotoOnlineEntity mt set mt.new = :new where mt.id = mt.id")
    fun setAllNew(@Param(value="new") new: Boolean)
    fun findByNew(new: Boolean): MutableIterable<MotoOnlineEntity>
}