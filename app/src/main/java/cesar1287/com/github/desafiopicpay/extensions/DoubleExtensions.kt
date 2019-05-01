package cesar1287.com.github.desafiopicpay.extensions

import java.text.NumberFormat
import java.util.*

fun Double.toBRL(): String {
    val locale = Locale("pt", "BR")
    return NumberFormat.getCurrencyInstance(locale).format(this)
}