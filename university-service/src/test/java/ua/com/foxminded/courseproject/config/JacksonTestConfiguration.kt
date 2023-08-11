package ua.com.foxminded.courseproject.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@Import(JacksonAutoConfiguration::class)
@AutoConfigureAfter(JacksonAutoConfiguration::class)
class JacksonTestConfiguration {
    @Bean
    fun webFluxConfigurer(objectMapper: ObjectMapper?): WebFluxConfigurer? {
        return object : WebFluxConfigurer {
            override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
                configurer.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
                configurer.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
            }
        }
    }

//    @Bean
//    fun webTestClient(
//        dayScheduleController: DayScheduleController,
//        studentController: StudentController, teacherController: TeacherController,objectMapper: ObjectMapper?
//    ):WebTestClient {
//        return WebTestClient.bindToController(dayScheduleController, studentController, teacherController)
//            .httpMessageCodecs {
//                val defaults = it.defaultCodecs()
//                defaults.jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, *arrayOfNulls<MimeType>(0)))
//                defaults.jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, *arrayOfNulls<MimeType>(0)))
//
//            }
//            .configureClient()
//            .build()
//    }


}