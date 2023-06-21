package ua.com.foxminded.restClient.dto

import java.time.ZonedDateTime

data class MonoRate(
    val date: ZonedDateTime? = null,
    val rateCross: Float? = null
) : Rate()