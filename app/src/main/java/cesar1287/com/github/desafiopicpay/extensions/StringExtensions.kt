package cesar1287.com.github.desafiopicpay.extensions

import java.text.NumberFormat
import java.util.*

fun String.getLast4CreditCardNumbers(): String {
    return try {
        this.substring(12, 16)
    } catch (indexOutOfBounds: IndexOutOfBoundsException) {
        ""
    }
}

fun String.replaceDesiredToBlank(regex: String): String {
    return this.replace(regex, "")
}

fun String.brlToDouble(): Double {
    return NumberFormat.getInstance(Locale("pt", "BR")).parse(this).toDouble()
}