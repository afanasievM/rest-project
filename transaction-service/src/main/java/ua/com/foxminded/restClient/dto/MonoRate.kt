package ua.com.foxminded.restClient.dto

import java.time.Instant

data class MonoRate(
    val date: Instant? = null,
    val rateCross: Float? = null
) : Rate()