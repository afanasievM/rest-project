package ua.com.foxminded.restClient.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.reactive.function.client.toEntityList
import reactor.core.publisher.Flux
import ua.com.foxminded.restClient.dto.MonoRate
import ua.com.foxminded.restClient.dto.Rate
import java.util.*

@Service
class MonoRateServiceImpl @Autowired constructor(
    private val webClient: WebClient,
) : RateService {
    @Value("\${mono.url}")
    private lateinit var URL: String


    override val rates: List<Rate>
        @Cacheable(value = ["currency"])
        get() {
            val response = webClient
                .get()
                .uri(URL)
                .retrieve()
                .bodyToMono<List<MonoRate>>()
                .block()
            return response
        }

}
