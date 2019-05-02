package cesar1287.com.github.desafiopicpay.core.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class MaskWatcher(private var editText: EditText, private var mask: String) : TextWatcher {

    var isUpdating: Boolean = false
    var old = ""

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(editable: Editable) {}

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        val string = unmask(charSequence.toString())
        var mask = ""
        if (isUpdating) {
            old = string
            isUpdating = false
            return
        }
        var index = 0
        for (item in this.mask.toCharArray()) {
            if (item != '#' && string.length > old.length) {
                mask += item
                continue
            }
            try {
                mask += string[index]
            } catch (exception: Exception) {
                break
            }

            index++
        }

        isUpdating = true
        editText.setText(mask)
        editText.setSelection(mask.length)
    }

    private fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "").replace("[/]".toRegex(), "")
            .replace("[(]".toRegex(), "").replace(
                "[ ]".toRegex(), ""
            ).replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
    }
}