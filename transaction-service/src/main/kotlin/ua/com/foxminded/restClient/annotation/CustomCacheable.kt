package ua.com.foxminded.restClient.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CustomCacheable(
    val value: String
)
