package ua.com.foxminded.restClient


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class RestClientApplication

fun main(args: Array<String>) {
    runApplication<RestClientApplication>(*args)
}