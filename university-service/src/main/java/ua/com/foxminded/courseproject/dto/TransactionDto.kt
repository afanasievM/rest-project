package ua.com.foxminded.courseproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import ua.com.foxminded.courseproject.enums.Direction;

import java.time.LocalDateTime;
import java.util.UUID;


public class TransactionDto {

    private UUID id;

    private UUID personId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionTime;

    private Direction transactionDirection;

    private Double value;

    private String currency;

    public TransactionDto() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Direction getTransactionDirection() {
        return transactionDirection;
    }

    public void setTransactionDirection(Direction transactionDirection) {
        this.transactionDirection = transactionDirection;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    private String iban;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionDto that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getPersonId() != null ? !getPersonId().equals(that.getPersonId()) : that.getPersonId() != null)
            return false;
        if (getTransactionTime() != null ? !getTransactionTime().equals(that.getTransactionTime()) : that.getTransactionTime() != null)
            return false;
        if (getTransactionDirection() != that.getTransactionDirection()) return false;
        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) return false;
        if (getCurrency() != null ? !getCurrency().equals(that.getCurrency()) : that.getCurrency() != null)
            return false;
        return getIban() != null ? getIban().equals(that.getIban()) : that.getIban() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getPersonId() != null ? getPersonId().hashCode() : 0);
        result = 31 * result + (getTransactionTime() != null ? getTransactionTime().hashCode() : 0);
        result = 31 * result + (getTransactionDirection() != null ? getTransactionDirection().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        result = 31 * result + (getIban() != null ? getIban().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", personId=" + personId +
                ", transactionTime=" + transactionTime +
                ", transactionDirection=" + transactionDirection +
                ", value=" + value +
                ", currency='" + currency + '\'' +
                ", iban='" + iban + '\'' +
                '}';
    }
}
