package ua.com.foxminded.restClient.gprc

import com.salesforce.grpc.contrib.spring.GrpcService
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Currency
import java.util.UUID
import org.springframework.data.domain.Pageable
import proto.ProtoMessage
import proto.ReactorTransactionsServiceGrpc
import reactor.core.publisher.Mono
import ua.com.foxminded.restClient.mapper.TransactionMapper
import ua.com.foxminded.restClient.mapper.listDtoToListResponse
import ua.com.foxminded.restClient.service.CurrencyExchangeService
import ua.com.foxminded.restClient.service.TransactionService

@GrpcService
class TransactionGRPCService(
    private val transactionService: TransactionService,
    private val exchangeService: CurrencyExchangeService,
    private val transactionMapper: TransactionMapper
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
            .collectList()
            .map {
                transactionMapper.listDtoToListResponse(it)
            }
    }
}
