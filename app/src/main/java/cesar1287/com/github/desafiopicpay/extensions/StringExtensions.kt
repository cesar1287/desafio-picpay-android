package cesar1287.com.github.desafiopicpay.extensions

import java.text.NumberFormat
import java.util.*

fun String.getLast4CreditCardNumbers(): String {
    return this.takeLast(4)
}

fun String.replaceDesiredToBlank(regex: String): String {
    return this.replace(regex, "")
}

fun String.brlToDouble(): Double {
    return NumberFormat.getInstance(Locale("pt", "BR")).parse(this).toDouble()
}

fun String.removeAllWhiteSpaces(): String {
    return this.replace("\\s".toRegex(), "")
}

fun String.unmask(): String {
    return this.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "").replace("[/]".toRegex(), "")
        .replace("[(]".toRegex(), "").replace(
            "[ ]".toRegex(), ""
        ).replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
}