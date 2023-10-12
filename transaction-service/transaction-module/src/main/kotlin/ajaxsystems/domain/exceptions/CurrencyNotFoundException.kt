package ajaxsystems.domain.exceptions

class CurrencyNotFoundException : RuntimeException {
    constructor() : super()
    constructor(s: String?, cause: Throwable?) : super(s, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(s: String?) : super(s)
    constructor(firstCurrency: String?, secondCurrency: String?) : super(
        MESSAGE.format(firstCurrency, secondCurrency)
    )

    companion object {
        private const val MESSAGE = "Currency rate between %s and %s not found."
    }
}
