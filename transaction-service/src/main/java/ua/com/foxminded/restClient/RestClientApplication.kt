package ua.com.foxminded.restClient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class RestClientApplication

fun main(args: Array<String>) {
    runApplication<RestClientApplication>(*args)
}