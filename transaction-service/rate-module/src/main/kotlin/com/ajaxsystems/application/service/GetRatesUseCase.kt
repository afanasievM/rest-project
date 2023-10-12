package com.ajaxsystems.application.service

import com.ajaxsystems.domain.dto.Rate
import org.springframework.stereotype.Service

@Service
class GetRatesUseCase(private val rateService: RateServiceOutPort) : GetRatesInPort {
    override fun getRates(): List<Rate> {
        return rateService.rates()
    }
}
