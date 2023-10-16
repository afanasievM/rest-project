package com.ajaxsystems.infrastructure.database.mapper

import com.ajaxsystems.domain.dto.TransactionDto
import com.ajaxsystems.infrastructure.database.mongodb.entity.Transaction
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface TransactionMapper {
    fun dtoToEntity(transactionDto: TransactionDto): Transaction
    fun entityToDto(transaction: Transaction): TransactionDto

}
