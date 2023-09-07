package ua.com.foxminded.restClient.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import ua.com.foxminded.restClient.annotation.CustomCacheable
import ua.com.foxminded.restClient.dto.MonoRate
import ua.com.foxminded.restClient.dto.Rate

@Service
class MonoRateServiceImpl(private val webClient: WebClient) : RateService {

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
