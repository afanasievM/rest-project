package utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import org.springframework.core.io.ClassPathResource
import proto.ProtoMessage
import ua.com.foxminded.restClient.dto.MonoRate
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.enums.Direction


object Transactions {

    val transactionRequest: ProtoMessage.FindTransactionsByPersonIdAndTimeRequest
        get() {
            val startDate = LocalDateTime.parse("2022-11-01T12:00:00").atZone(ZoneId.systemDefault()).toInstant()
            val endDate = LocalDateTime.parse("2022-12-02T12:00:00").atZone(ZoneId.systemDefault()).toInstant()
            return ProtoMessage.FindTransactionsByPersonIdAndTimeRequest.newBuilder().apply {
                personId = "e966f608-4621-11ed-b878-0242ac120002"
                currency = "UAH"
                startDateBuilder.setSeconds(startDate.epochSecond).setNanos(startDate.nano)
                endDateBuilder.setSeconds(endDate.epochSecond).setNanos(endDate.nano)
                page = 0
                size = 2
            }.build()
        }

    val transactionList: List<TransactionDto>
        get() {
            val dto1 = TransactionDto(
                id = UUID.fromString("e966f601-4621-11ed-b878-0242ac120002"),
                personId = UUID.fromString("e966f608-4621-11ed-b878-0242ac120002"),
                transactionTime = LocalDateTime.parse("2022-12-01T12:00"),
                transactionDirection = Direction.OUTPUT,
                value = 36650.001525878906,
                currency = "UAH",
                iban = "GB29NWBK60161331926819"
            )
            val dto2 = TransactionDto(
                id = UUID.fromString("e966f602-4621-11ed-b878-0242ac120002"),
                personId = UUID.fromString("e966f608-4621-11ed-b878-0242ac120002"),
                transactionTime = LocalDateTime.parse("2022-12-02T12:00"),
                transactionDirection = Direction.OUTPUT,
                value = 36650.001525878906,
                currency = "UAH",
                iban = "GB29NWBK60161331926819"
            )
            return listOf(dto1, dto2)
        }

    val rates: List<MonoRate>
        get() {
            val jsonString = ClassPathResource("rates.json").file.readText()
            val mapper =  jacksonObjectMapper().apply { registerModule(JavaTimeModule()) }
            return mapper.readValue(jsonString, object : TypeReference<ArrayList<MonoRate>>() {})
        }
}
