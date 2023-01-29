package ru.rmatyuk.parsemoto.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import ru.rmatyuk.parsemoto.repository.ConfigRepository


@Configuration
@EnableScheduling
class SpringConfig(configRepository: ConfigRepository) {

    private val configRepository: ConfigRepository

    init {
        this.configRepository = configRepository
    }

    @Bean
    fun getCronOnlineAuction(): String {
        return configRepository.findById("cron_online_auction").get().configValue!!
    }

    @Bean
    fun getCronHistoryAuction(): String {
        return configRepository.findById("cron_history_auction").get().configValue!!
    }
}