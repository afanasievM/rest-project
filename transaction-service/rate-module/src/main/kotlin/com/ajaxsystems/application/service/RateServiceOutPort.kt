package com.ajaxsystems.application.service

import com.ajaxsystems.domain.dto.Rate

interface RateServiceOutPort {
    fun rates(): List<Rate>
}
