package cesar1287.com.github.desafiopicpay.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.showToast(message: String, duration: Int) {
    Toast.makeText(this, message, duration).show()
}

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}