package ua.com.foxminded.restClient.entity

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import ua.com.foxminded.restClient.enums.Direction
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    var id: UUID? = null,

    @Column(name = "person_id")
    @Type(type = "uuid-char")
    var personId: UUID? = null,

    @Column(name = "transaction_time")
    var transactionTime: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_direction")
    var transactionDirection: Direction? = null,

    @Column(name = "value", precision = 10, scale = 2)
    var value: Double? = null,

    @Column(name = "currency")
    var currency: String? = null,

    @Column(name = "iban")
    var iban: String? = null
)