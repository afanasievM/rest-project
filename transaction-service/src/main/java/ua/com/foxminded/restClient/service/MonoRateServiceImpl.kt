package ua.com.foxminded.restClient.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ua.com.foxminded.restClient.dto.MonoRate
import java.util.*

@Service
class MonoRateServiceImpl @Autowired constructor(
    private val restTemplate: RestTemplate,
) : RateService {
    @Value("\${mono.url}")
    private lateinit var URL: String


    override val rates: List<MonoRate>
        @Cacheable(value = ["currency"])
        get() {
            val response = restTemplate.getForEntity(URL, Array<MonoRate>::class.java)
            val rates = response.body.toList()
            return rates
        }

}
