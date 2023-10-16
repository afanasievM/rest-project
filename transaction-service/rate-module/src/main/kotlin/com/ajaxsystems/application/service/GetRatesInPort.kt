package com.ajaxsystems.application.service

import com.ajaxsystems.domain.dto.Rate

interface GetRatesInPort {
    fun getRates(): List<Rate>
}
