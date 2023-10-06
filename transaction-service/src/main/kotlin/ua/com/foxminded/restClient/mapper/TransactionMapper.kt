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

fun TransactionMapper.dtoToProto(
    dto: TransactionDto
): ProtoMessage.Transaction {
    return ProtoMessage.Transaction.newBuilder()
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

fun TransactionMapper.listDtoToListResponse(
    dtos: List<TransactionDto>
): ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse {
    return ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse
        .newBuilder()
        .apply {
            success = successBuilder.addAllTransactions(dtos.map { dtoToProto(it) }).build()
        }
        .build()
}


