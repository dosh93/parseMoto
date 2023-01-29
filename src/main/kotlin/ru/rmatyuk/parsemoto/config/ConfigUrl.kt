package ru.rmatyuk.parsemoto

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("application.yml")
data class ConfigUrl(
        @Value("\${url.template.online}")
        val urlTemplateOnline: String,

        @Value("\${url.template.history}")
        val urlTemplateHistory: String
)