package ua.com.foxminded.restClient

import io.nats.client.Nats
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.Duration
import java.util.*

@SpringBootApplication
class RestClientApplication

fun main(args: Array<String>) {

    try {
        Nats.connect("nats://0.0.0.0:4222").use { nc ->
            print("Waiting for a message...")

            val sub = nc.subscribe("kotlin-nats-demo")
            nc.flush(Duration.ofSeconds(5))

            val msg = sub.nextMessage(Duration.ofHours(1))
            println("Received ${msg.subject} ${String(msg.data)}")
        }
    } catch (exp: Exception) {
        exp.printStackTrace()
    }
//    runApplication<RestClientApplication>(*args)
}