package com.ajaxsystems.infrastructure.service

import com.ajaxsystems.application.service.RateServiceOutPort
import com.ajaxsystems.domain.dto.MonoRate
import com.ajaxsystems.domain.dto.Rate
import com.ajaxsystems.infrastructure.cache.annotation.CustomCacheable
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class MonoRateServiceImpl(private val webClient: WebClient) : RateServiceOutPort {

    @Value("\${mono.url}")
    private lateinit var url: String

    @CustomCacheable(CACHE_KEY)
    override fun rates(): List<Rate> = webClient
        .get()
        .uri(url)
        .retrieve()
        .bodyToMono<List<MonoRate>>()
        .block().orEmpty()

    companion object {
        const val CACHE_KEY = "currency"
    }
}
