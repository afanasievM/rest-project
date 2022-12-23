package ua.com.foxminded.restClient.exceptions;

public class CurrencyNotFoundException extends RuntimeException {

    static private final String message = "Currency rate between %s and %s not found.";

    public CurrencyNotFoundException() {
        super();
    }

    public CurrencyNotFoundException(String s, Throwable cause) {
        super(s, cause);
    }

    public CurrencyNotFoundException(Throwable cause) {
        super(cause);
    }

    public CurrencyNotFoundException(String s) {
        super(s);
    }

    public CurrencyNotFoundException(String firstCurrency, String secondCurrency) {
        super(message.formatted(firstCurrency, secondCurrency));
    }
}
