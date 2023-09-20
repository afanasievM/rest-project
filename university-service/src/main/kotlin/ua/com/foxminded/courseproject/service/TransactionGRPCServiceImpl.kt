package ua.com.foxminded.courseproject.service

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.UUID
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import protobuf.ProtoMessage
import protobuf.ReactorTransactionsServiceGrpc
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.TransactionDto
import ua.com.foxminded.courseproject.enums.Direction

@Service
class TransactionGRPCServiceImpl(
    val stub: ReactorTransactionsServiceGrpc.ReactorTransactionsServiceStub
) : TransactionService {

    override fun getTransactions(
        id: String,
        currency: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        pageable: Pageable
    ): Mono<List<TransactionDto>> {
        return stub
            .getTransactions(Mono.just(buildTransactionRequest(id, currency, startDate, endDate, pageable)))
            .map {
                it.transactionList.map { transaction -> transactionResponseToDto(transaction) }
            }
    }

    private fun buildTransactionRequest(
        id: String,
        cur: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        pageable: Pageable
    ): ProtoMessage.TransactionRequestProto {
        val startDate = startDate.atZone(ZoneId.systemDefault()).toInstant()
        val endDate = endDate.atZone(ZoneId.systemDefault()).toInstant()
        return ProtoMessage.TransactionRequestProto.newBuilder().apply {
            personId = id
            currency = cur
            startDateBuilder.setSeconds(startDate.epochSecond).setNanos(startDate.nano)
            endDateBuilder.setSeconds(endDate.epochSecond).setNanos(endDate.nano)
            page = pageable.pageNumber
            size = pageable.pageSize
        }.build()
    }

    private fun transactionResponseToDto(responseProto: ProtoMessage.TransactionResponseProto): TransactionDto {
        return TransactionDto().apply {
            id = UUID.fromString(responseProto.id)
            personId = UUID.fromString(responseProto.personId)
            transactionTime = LocalDateTime.ofEpochSecond(
                responseProto.transactionTime.seconds,
                responseProto.transactionTime.nanos,
                ZoneOffset.UTC
            )
            transactionDirection = Direction.valueOf(responseProto.direction.toString())
            value = responseProto.value
            currency = responseProto.currency
            iban = responseProto.iban
        }
    }

}
