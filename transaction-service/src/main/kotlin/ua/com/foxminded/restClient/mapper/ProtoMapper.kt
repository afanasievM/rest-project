package ua.com.foxminded.restClient.mapper

import com.google.protobuf.Timestamp
import java.time.ZoneOffset
import org.springframework.stereotype.Component
import proto.ProtoMessage
import ua.com.foxminded.restClient.dto.TransactionDto

@Component
class ProtoMapper {

    fun mapTransactionDtoToResponse(
        dto: TransactionDto
    ): ProtoMessage.FindTransactionsByPersonIdAndTimeResponse {
        return ProtoMessage.FindTransactionsByPersonIdAndTimeResponse.newBuilder()
            .setId(dto.id.toString())
            .setPersonId(dto.personId.toString())
            .setTransactionTime(
                Timestamp.newBuilder()
                    .setSeconds(dto.transactionTime?.toEpochSecond(ZoneOffset.UTC) ?: 0)
                    .setNanos(dto.transactionTime?.nano ?: 0)
            )
            .setValue(dto.value ?: 0.0)
            .setCurrency(dto.currency)
            .setIban(dto.iban)
            .build()
    }
}
