package ru.rmatyuk.parsemoto.config

import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.entity.ConfigEntity
import ru.rmatyuk.parsemoto.repository.ConfigRepository

@Component
class OnApplicationStartUp(configRepository: ConfigRepository) {

    private final val configRepository: ConfigRepository

    init {
        this.configRepository = configRepository
    }

    @EventListener
    fun onApplicationEvent(event: ContextRefreshedEvent?) {
        val allConfig = mutableListOf<ConfigEntity>()
        if (configRepository.findById("cron_online_auction").isEmpty) {
            allConfig.add(ConfigEntity("cron_online_auction", "Расписание считывагия онлайн аукциона", "0 0 0 * * ?"))
        }
        if (configRepository.findById("cron_history_auction").isEmpty) {
            allConfig.add(ConfigEntity("cron_history_auction", "Расписание считывагия истории аукциона", "0 0 3 * * ?"))
        }
        if (configRepository.findById("start_page_history_auction").isEmpty) {
            allConfig.add(ConfigEntity("start_page_history_auction", "Страница с какой считывать историю аукциона", "1"))
        }
        if (configRepository.findById("count_page_history_auction").isEmpty) {
            allConfig.add(ConfigEntity("count_page_history_auction", "Количество страниц истории аукциона", "5000"))
        }
        if (configRepository.findById("start_page_online_auction").isEmpty) {
            allConfig.add(ConfigEntity("start_page_online_auction", "Страница с какой считывать онлайн аукциона", "1"))
        }
        if (configRepository.findById("count_page_online_auction").isEmpty) {
            allConfig.add(ConfigEntity("count_page_online_auction", "Количество страниц онлайн аукциона"))
        }
        if (configRepository.findById("enabled_task_online_auction").isEmpty) {
            allConfig.add(ConfigEntity("enabled_task_online_auction", "Включено парсик онлайн аукциона", "true"))
        }
        if (configRepository.findById("enabled_task_history_auction").isEmpty) {
            allConfig.add(ConfigEntity("enabled_task_history_auction", "Включено парсик истории аукциона", "true"))
        }
        configRepository.saveAll(allConfig)
    }
}