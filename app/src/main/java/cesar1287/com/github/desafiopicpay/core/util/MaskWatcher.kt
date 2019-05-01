package cesar1287.com.github.desafiopicpay.core.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MaskWatcher(private var ediTxt: EditText, private var mask: String) : TextWatcher {

    var isUpdating: Boolean = false
    var old = ""

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(editable: Editable) {}

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        val str = unmask(charSequence.toString())
        var mascara = ""
        if (isUpdating) {
            old = str
            isUpdating = false
            return
        }
        var i = 0
        for (m in mask.toCharArray()) {
            if (m != '#' && str.length > old.length) {
                mascara += m
                continue
            }
            try {
                mascara += str[i]
            } catch (e: Exception) {
                break
            }

            i++
        }

        isUpdating = true
        ediTxt.setText(mascara)
        ediTxt.setSelection(mascara.length)
    }

    private fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "").replace("[/]".toRegex(), "")
            .replace("[(]".toRegex(), "").replace(
                "[ ]".toRegex(), ""
            ).replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
    }
}