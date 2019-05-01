package cesar1287.com.github.desafiopicpay.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView
import androidx.core.content.ContextCompat
import cesar1287.com.github.desafiopicpay.R


fun Context.showToast(message: String, duration: Int) {
    val toast = Toast.makeText(this, message, duration)
    val view = toast.view
    val text = view.findViewById(android.R.id.message) as TextView
    text.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
    toast.show()
}

fun View.showSnackBar(context: Context, message: String, duration: Int) {
    val snackbar = Snackbar.make(this, message, duration)
    val view = snackbar.view
    view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    snackbar.show()
}