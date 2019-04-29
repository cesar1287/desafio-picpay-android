package cesar1287.com.github.desafiopicpay.features.creditCard.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_CARD_NUMBER
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_CVV
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_EXPIRY_DATE
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_NAME
import cesar1287.com.github.desafiopicpay.features.BaseActivity
import cesar1287.com.github.desafiopicpay.features.creditCard.viewmodel.CreditCardViewModel
import kotlinx.android.synthetic.main.activity_credit_card.*

class CreditCardActivity : BaseActivity() {

    private var creditCardViewModel: CreditCardViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)

        setupObservables()
        setupToolbar(tbCreditCardToolbar)
    }

    private fun setupObservables() {
        creditCardViewModel = ViewModelProviders.of(this).get(CreditCardViewModel::class.java)

        creditCardViewModel?.getAllCreditCards()

        btCreditCardSave.setOnClickListener {
            val creditCardHashMap: HashMap<String, String> = hashMapOf(
                KEY_HASH_CARD_NUMBER to tieCreditCardNumber.text.toString(),
                KEY_HASH_NAME to tieCreditCardName.text.toString(),
                KEY_HASH_EXPIRY_DATE to tieCreditCardExpire.text.toString(),
                KEY_HASH_CVV to tieCreditCardCvv.text.toString()
            )

            creditCardViewModel?.save(creditCardHashMap)
        }
    }
}
