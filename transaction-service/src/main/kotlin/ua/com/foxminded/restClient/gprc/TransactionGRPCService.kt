package ua.com.foxminded.restClient.gprc

import com.salesforce.grpc.contrib.spring.GrpcService
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Currency
import java.util.UUID
import org.springframework.data.domain.Pageable
import protobuf.ProtoMessage
import protobuf.ReactorTransactionsServiceGrpc
import reactor.core.publisher.Mono
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService

@GrpcService
class TransactionGRPCService(
    private val transactionService: TransactionService,
    private val exchangeService: CurrencyExchangeService,
) : ReactorTransactionsServiceGrpc.TransactionsServiceImplBase() {

    override fun findTransactionsByPersonIdAndTime(
        request: Mono<ProtoMessage.FindTransactionsByPersonIdAndTimeRequest>
    ): Mono<ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse>? {
        var currency = ""
        return request
            .doOnNext { currency = it.currency }
            .flatMapMany {
                transactionService.findAllByIdAndBetweenDate(
                    UUID.fromString(it.personId),
                    LocalDateTime.ofEpochSecond(it.startDate.seconds, it.endDate.nanos, ZoneOffset.UTC),
                    LocalDateTime.ofEpochSecond(it.endDate.seconds, it.endDate.nanos, ZoneOffset.UTC),
                    Pageable.ofSize(it.size).withPage(it.page)
                )
            }
            .map { exchangeService.exchangeTo(it, Currency.getInstance(currency)) }
            .map(this::mapTransactionDtoToResponse)
            .collectList()
            .map {
                ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse.newBuilder()
                    .addAllTransaction(it)
                    .build()
            }
    }

    private fun mapTransactionDtoToResponse(
        dto: TransactionDto
    ): ProtoMessage.FindTransactionsByPersonIdAndTimeResponse {
        return ProtoMessage.FindTransactionsByPersonIdAndTimeResponse.newBuilder().apply {
            id = dto.id.toString()
            personId = dto.personId.toString()
            transactionTimeBuilder
                .setSeconds(dto.transactionTime?.toEpochSecond(ZoneOffset.UTC) ?: 0)
                .setNanos(dto.transactionTime?.nano ?: 0)
            value = dto.value ?: 0.0
            currency = dto.currency
            iban = dto.iban
        }.build()
    }

}
