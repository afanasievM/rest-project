package ua.com.foxminded.restClient.entity

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ua.com.foxminded.restClient.enums.Direction
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Document("transactions")
data class Transaction(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    var id: UUID? = null,
    var personId: UUID? = null,
    var transactionTime: LocalDateTime? = null,
    var transactionDirection: Direction? = null,
    var value: Double? = null,
    var currency: String? = null,
    var iban: String? = null
)