package ua.com.foxminded.restClient.service

import ua.com.foxminded.restClient.dto.Rate

interface RateService {
    fun rates(): List<Rate>
}
