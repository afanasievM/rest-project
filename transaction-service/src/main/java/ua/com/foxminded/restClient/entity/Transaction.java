package ua.com.foxminded.restClient.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.com.foxminded.restClient.enums.Direction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "person_id")
    @Type(type = "uuid-char")
    private UUID personId;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_direction")
    private Direction transactionDirection;

    @Column(name = "value", precision = 10, scale = 2)
    private Double value;

    @Column(name = "currency")
    private String currency;

    @Column(name = "iban")
    private String iban;

}
