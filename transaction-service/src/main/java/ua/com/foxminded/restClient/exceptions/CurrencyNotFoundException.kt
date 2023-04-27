package ua.com.foxminded.restClient.exceptions

class CurrencyNotFoundException : RuntimeException {
    constructor() : super()
    constructor(s: String?, cause: Throwable?) : super(s, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(s: String?) : super(s)
    constructor(firstCurrency: String?, secondCurrency: String?) : super(
        Companion.message.format(
            firstCurrency,
            secondCurrency
        )
    )

    companion object {
        private const val message = "Currency rate between %s and %s not found."
    }
}
