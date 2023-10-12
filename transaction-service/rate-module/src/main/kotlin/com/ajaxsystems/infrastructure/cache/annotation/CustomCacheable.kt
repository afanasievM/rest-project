package com.ajaxsystems.infrastructure.cache.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CustomCacheable(
    val value: String
)
