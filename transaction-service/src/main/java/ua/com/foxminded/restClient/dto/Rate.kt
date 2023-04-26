package ua.com.foxminded.restClient.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Rate {
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private Float rateSell;
    private Float rateBuy;
}
