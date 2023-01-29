package ru.rmatyuk.parsemoto.scheduled

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.repository.ConfigRepository
import ru.rmatyuk.parsemoto.service.TaskService

@Component
class Scheduled(taskService: TaskService, configRepository: ConfigRepository) {

    private val taskService: TaskService
    private val configRepository: ConfigRepository

    init {
        this.taskService = taskService
        this.configRepository = configRepository
    }

    @Scheduled(cron = "#{@getCronOnlineAuction}")
    fun scheduledSaveOnlineAuction() {
        val startPage = configRepository.findById("start_page_online_auction").get().configValue!!.toInt()
        val countPage = configRepository.findById("count_page_online_auction").get().configValue
        if (countPage == null) {
            taskService.onlineAuction(startPage)
        } else {
            taskService.onlineAuction(startPage, countPage.toInt())
        }
    }

    @Scheduled(cron = "#{@getCronHistoryAuction}")
    fun scheduledSaveHistoryAuction() {
        val startPage = configRepository.findById("start_page_history_auction").get().configValue!!.toInt()
        val countPage = configRepository.findById("count_page_history_auction").get().configValue
        if (countPage == null) {
            taskService.historyAuction(startPage)
        } else {
            taskService.historyAuction(startPage, countPage.toInt())
        }
    }
}
