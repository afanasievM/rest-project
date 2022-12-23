package ua.com.foxminded.restClient.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.com.foxminded.restClient.dto.TransactionDto;
import ua.com.foxminded.restClient.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    Transaction dtoToEntity(TransactionDto transaction);

    TransactionDto entityToDto(Transaction transactionDto);
}
