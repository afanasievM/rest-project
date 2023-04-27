package ua.com.foxminded.restClient.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ua.com.foxminded.restClient.dto.MonoRate
import ua.com.foxminded.restClient.dto.Rate
import java.util.*

@Service
open class MonoRateServiceImpl @Autowired constructor(private val restTemplate: RestTemplate) : RateService {
    @Value("\${mono.url}")
    private lateinit var URL: String

    override val rates: List<Rate>
        get() {
            val response = restTemplate.getForEntity(URL, Array<MonoRate>::class.java)
            val rates = response.body
            return Arrays.stream(rates as Array<Rate>).toList()
        }
}
