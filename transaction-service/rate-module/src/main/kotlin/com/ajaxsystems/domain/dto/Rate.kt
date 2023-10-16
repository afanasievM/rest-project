package com.ajaxsystems.domain.dto


open class Rate(
    val currencyCodeA: Int? = null,
    val currencyCodeB: Int? = null,
    val rateSell: Float? = null,
    val rateBuy: Float? = null
)
