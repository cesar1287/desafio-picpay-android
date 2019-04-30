package cesar1287.com.github.desafiopicpay.features.payment.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.model.TransationResponse
import cesar1287.com.github.desafiopicpay.core.util.GlideApp
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_TRANSACTION
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.receipt_bottom_sheet.*

class ReceiptBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.receipt_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transationResponse = arguments?.get(KEY_EXTRA_TRANSACTION) as? TransationResponse

        transationResponse?.let {
            GlideApp.with(this).load(it.transaction.destinationUser.img).into(ivReceiptAvatar)

            tvReceiptUsername.text = it.transaction.destinationUser.username
            tvReceiptDate.text = it.transaction.timestamp.toString()
            tvReceiptTransactionId.text = it.transaction.id.toString()
            tvReceiptCreditCardValue.text = it.transaction.value.toString()
            tvReceiptTotalValue.text = it.transaction.value.toString()
        }
    }
}