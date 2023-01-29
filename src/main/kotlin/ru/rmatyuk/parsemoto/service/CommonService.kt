package ru.rmatyuk.parsemoto.service

import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.dto.CommonField
import ru.rmatyuk.parsemoto.entity.AuctionEntity
import ru.rmatyuk.parsemoto.entity.MarkEntity
import ru.rmatyuk.parsemoto.entity.ModelEntity
import ru.rmatyuk.parsemoto.entity.StatusEntity

@Component
class CommonService(
        auctionService: AuctionService, statusService: StatusService,
        markService: MarkService, modelService: ModelService
) {

    private final val auctionService: AuctionService
    private final val statusService: StatusService
    private final val markService: MarkService
    private final val modelService: ModelService

    init {
        this.auctionService = auctionService
        this.statusService = statusService
        this.markService = markService
        this.modelService = modelService
    }

    fun saveCommonFiled(auction: String, status: String, mark: String, model: String): CommonField {
        val auctionEntity = auctionService.save(AuctionEntity(name = auction))
        val statusEntity = statusService.save(StatusEntity(name = status))
        val markEntity = markService.save(MarkEntity(name = mark))
        val modelEntity = modelService.save(ModelEntity(name = model, mark = markEntity))
        return CommonField(auctionEntity, statusEntity, markEntity, modelEntity)
    }
}