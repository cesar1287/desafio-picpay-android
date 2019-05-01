package cesar1287.com.github.desafiopicpay.extensions

fun String.getLast4CreditCardNumbers(): String {
    return try {
        this.substring(12, 16)
    } catch (indexOutOfBounds: IndexOutOfBoundsException) {
        ""
    }
}