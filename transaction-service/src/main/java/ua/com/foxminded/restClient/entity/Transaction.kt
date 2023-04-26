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
    private val id: UUID? = null,

    @Column(name = "person_id")
    @Type(type = "uuid-char")
    private val personId: UUID? = null,

    @Column(name = "transaction_time")
    private val transactionTime: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_direction")
    private val transactionDirection: Direction? = null,

    @Column(name = "value", precision = 10, scale = 2)
    private val value: Double? = null,

    @Column(name = "currency")
    private val currency: String? = null,

    @Column(name = "iban")
    private val iban: String? = null
)