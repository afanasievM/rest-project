package ua.com.foxminded.restClient.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.com.foxminded.restClient.enums.Direction;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TransactionDto {

    private UUID id;

    private UUID personId;

    private LocalDateTime transactionTime;

    private Direction transactionDirection;

    private Double value;

    private String currency;

    private String iban;

}
