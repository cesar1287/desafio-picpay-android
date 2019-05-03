package cesar1287.com.github.desafiopicpay.features.payment.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_EXTRA_CREDIT_CARD_LIST
import cesar1287.com.github.desafiopicpay.features.payment.adapter.CreditCardAdapter
import cesar1287.com.github.desafiopicpay.features.payment.listener.CreditCardSelectionListener
import cesar1287.com.github.desafiopicpay.features.payment.viewmodel.PaymentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.credit_card_bottom_sheet.*

class CreditCardBottomSheetFragment : BottomSheetDialogFragment(), CreditCardSelectionListener {

    private var paymentViewModel: PaymentViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.credit_card_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentViewModel = activity?.let { ViewModelProviders.of(it).get(PaymentViewModel::class.java) }

        val creditCardsList = arguments?.get(KEY_EXTRA_CREDIT_CARD_LIST) as? Array<CreditCard>

        val layoutManager = LinearLayoutManager(context)
        rvCreditCardsContent.layoutManager = layoutManager
        val creditCardAdapter = CreditCardAdapter(creditCardsList, this)
        rvCreditCardsContent.adapter = creditCardAdapter
    }

    override fun onCreditCardSelected(creditCard: CreditCard?) {
        paymentViewModel?.setCreditCardSelected(creditCard)
        dismiss()
    }
}