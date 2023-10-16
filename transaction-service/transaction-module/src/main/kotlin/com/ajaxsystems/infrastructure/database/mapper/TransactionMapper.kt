package com.ajaxsystems.infrastructure.database.mapper

import com.ajaxsystems.domain.dto.TransactionDto
import com.ajaxsystems.infrastructure.database.mongodb.entity.Transaction
import com.google.protobuf.Timestamp
import java.time.ZoneOffset
import org.mapstruct.Mapper
import proto.ProtoMessage

@Mapper(componentModel = "spring")
interface TransactionMapper {
    fun dtoToEntity(transactionDto: TransactionDto): Transaction
    fun entityToDto(transaction: Transaction): TransactionDto

}

fun TransactionDto.toProto(): ProtoMessage.Transaction {
    return ProtoMessage.Transaction.newBuilder()
        .setId(this.id.toString())
        .setPersonId(this.personId.toString())
        .setTransactionTime(
            Timestamp.newBuilder()
                .setSeconds(this.transactionTime?.toEpochSecond(ZoneOffset.UTC) ?: 0)
                .setNanos(this.transactionTime?.nano ?: 0)
        )
        .setValue(this.value ?: 0.0)
        .setCurrency(this.currency)
        .setIban(this.iban)
        .build()
}

fun List<TransactionDto>.toListResponse(): ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse {
    val protoList = this.map { it.toProto() }
    return ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse
        .newBuilder()
        .apply {
            success = successBuilder.addAllTransactions(protoList).build()
        }
        .build()
}


