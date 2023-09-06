package ua.com.foxminded.restClient.mapper

import org.mapstruct.Mapper
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.entity.Transaction

@Mapper(componentModel = "spring")
interface TransactionMapper {
    fun dtoToEntity(transactionDto: TransactionDto): Transaction
    fun entityToDto(transaction: Transaction): TransactionDto

}
