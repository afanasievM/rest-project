package ua.com.foxminded.restClient.mapper

import com.google.protobuf.Timestamp
import java.time.ZoneOffset
import org.mapstruct.Mapper
import proto.ProtoMessage
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.entity.Transaction

@Mapper(componentModel = "spring")
interface TransactionMapper {
    fun dtoToEntity(transactionDto: TransactionDto): Transaction
    fun entityToDto(transaction: Transaction): TransactionDto

}

fun TransactionMapper.dtoToProtoResponse(
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
