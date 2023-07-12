package ua.com.foxminded.restClient.service

import reactor.core.publisher.Flux
import ua.com.foxminded.restClient.dto.Rate

interface RateService {
    val rates: List<Rate>
}
