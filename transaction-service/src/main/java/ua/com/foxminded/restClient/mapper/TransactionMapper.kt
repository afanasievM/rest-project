package ua.com.foxminded.restClient.mapper

import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import ua.com.foxminded.restClient.dto.TransactionDto
import ua.com.foxminded.restClient.entity.Transaction

@Mapper(componentModel = "spring")
interface TransactionMapper {
    fun dtoToEntity(transaction: TransactionDto?): Transaction?
    fun entityToDto(transactionDto: Transaction?): TransactionDto?
}