package ajaxsystems.infrastructure.grpc.service

import ajaxsystems.application.useCases.FindTransactionsRestApiInputPort
import ajaxsystems.infrastructure.mapper.toListResponse
import com.salesforce.grpc.contrib.spring.GrpcService
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID
import org.springframework.data.domain.Pageable
import proto.ProtoMessage
import proto.ReactorTransactionsServiceGrpc
import reactor.core.publisher.Mono

@GrpcService
class TransactionGRPCService(
    private val useCase: FindTransactionsRestApiInputPort
) : ReactorTransactionsServiceGrpc.TransactionsServiceImplBase() {

    override fun findTransactionsByPersonIdAndTime(
        request: Mono<ProtoMessage.FindTransactionsByPersonIdAndTimeRequest>
    ): Mono<ProtoMessage.FindTransactionsByPersonIdAndTimeListResponse>? {
        return request
            .flatMapMany {
                useCase.findAllByIdAndBetweenDateAndExchangeCurrency(
                    UUID.fromString(it.personId),
                    LocalDateTime.ofEpochSecond(it.startDate.seconds, it.endDate.nanos, ZoneOffset.UTC),
                    LocalDateTime.ofEpochSecond(it.endDate.seconds, it.endDate.nanos, ZoneOffset.UTC),
                    Pageable.ofSize(it.size).withPage(it.page),
                    it.currency
                )
            }
            .map { it.toListResponse() }
            .single()
    }
}
