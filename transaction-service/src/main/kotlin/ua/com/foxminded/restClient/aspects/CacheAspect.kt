package ua.com.foxminded.restClient.aspects

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import ua.com.foxminded.restClient.annotation.CustomCacheable

@Aspect
@Component
class CacheAspect(private val cacheManager: CacheManager) {

    @Suppress("EmptyFunctionBlock", "UnusedParameter")
    @Pointcut("@annotation(customCacheable)")
    fun customCacheablePointcut(customCacheable: CustomCacheable) {
    }

    @Around("customCacheablePointcut(customCacheable)")
    fun cacheResult(joinPoint: ProceedingJoinPoint, customCacheable: CustomCacheable): Any {
        val cacheName = customCacheable.value
        return when (val cacheValue = cacheManager.getCache(cacheName)?.get(cacheName)) {
            null -> {
                val result = joinPoint.proceed()
                cacheManager.getCache(cacheName)?.put(cacheName, result)
                result
            }

            else -> cacheValue.get()
        }

    }
}
