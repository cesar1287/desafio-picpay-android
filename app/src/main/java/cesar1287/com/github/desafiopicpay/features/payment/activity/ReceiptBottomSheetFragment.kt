package cesar1287.com.github.desafiopicpay.features.payment.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import cesar1287.com.github.desafiopicpay.core.model.TransactionResponse
import cesar1287.com.github.desafiopicpay.core.util.GlideApp
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_CREDIT_CARD
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_TRANSACTION
import cesar1287.com.github.desafiopicpay.extensions.getLast4CreditCardNumbers
import cesar1287.com.github.desafiopicpay.extensions.toBRL
import cesar1287.com.github.desafiopicpay.extensions.toFormattedDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.receipt_bottom_sheet.*

class ReceiptBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.receipt_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transactionResponse = arguments?.get(KEY_EXTRA_TRANSACTION) as? TransactionResponse
        val creditCard = arguments?.get(KEY_EXTRA_CREDIT_CARD) as? CreditCard

        transactionResponse?.let { transactionResponseNonNull ->
            creditCard?.let {
                GlideApp.with(this).load(transactionResponseNonNull.transaction.destinationUser.img).into(ivReceiptAvatar)

                tvReceiptUsername.text = transactionResponseNonNull.transaction.destinationUser.username
                tvReceiptDate.text = transactionResponseNonNull.transaction.timestamp.toFormattedDate()
                tvReceiptTransactionId.text = transactionResponseNonNull.transaction.id.toString()

                tvReceiptCreditCard.text = String.format(requireContext().getString(R.string.receipt_credit_card),
                    it.cardNumber?.getLast4CreditCardNumbers())
                tvReceiptCreditCardValue.text = transactionResponseNonNull.transaction.value.toBRL()
                tvReceiptTotalValue.text = transactionResponseNonNull.transaction.value.toBRL()
            }
        }
    }
}