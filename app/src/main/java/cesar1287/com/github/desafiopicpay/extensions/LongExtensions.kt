package cesar1287.com.github.desafiopicpay.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.toFormattedDate(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000L
    val dateFormat = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault())
    return dateFormat.format(calendar.time)
}