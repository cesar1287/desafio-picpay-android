package cesar1287.com.github.desafiopicpay.core.util

import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import cesar1287.com.github.desafiopicpay.extensions.replaceDesiredToBlank
import java.lang.NumberFormatException
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class MoneyTextWatcher(private var editTextWeakReference: WeakReference<EditText>) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        val editText = editTextWeakReference.get() ?: return
        editText.removeTextChangedListener(this)
        val locale = Locale("pt", "BR")

        val parsed = parseToBigDecimal(editable.toString(), locale)
        val formatted = NumberFormat.getCurrencyInstance(locale).format(parsed).replaceDesiredToBlank("R$").trim()

        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }

    private fun parseToBigDecimal(value: String, locale: Locale): BigDecimal {
        val replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).currency.symbol)

        val cleanString = value.replace(replaceable.toRegex(), "")

        return try {
            BigDecimal(cleanString).setScale(
                2, BigDecimal.ROUND_FLOOR
            ).divide(
                BigDecimal(100), BigDecimal.ROUND_FLOOR
            )
        } catch (numberFormat: NumberFormatException) {
            BigDecimal("0")
        }
    }
}