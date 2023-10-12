package ajaxsystems.infrastructure.database.mongodb.entity

import ajaxsystems.domain.enums.Direction
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType

@Document("transactions")
data class Transaction(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    var id: UUID? = UUID.randomUUID(),
    @Field("person_id", targetType = FieldType.STRING)
    var personId: UUID? = null,
    @Field("transaction_time")
    var transactionTime: LocalDateTime? = null,
    @Field("transaction_direction")
    var transactionDirection: Direction? = null,
    @Field("value")
    var value: Double? = null,
    @Field("currency")
    var currency: String? = null,
    @Field("iban")
    var iban: String? = null
)
