package com.example.dayflower.sbvr

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestTemplate

@Configuration
class ApplicationConfiguration {
    @Profile("local")
    @Bean
    fun webMvcRegistrationsForLocal(): WebMvcRegistrations {
        return object : WebMvcRegistrations {
            override fun getRequestMappingHandlerMapping() =
                RequestMappingHandlerFallbackMapping()
        }
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder().build()
    }
}
