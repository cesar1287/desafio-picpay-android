package cesar1287.com.github.desafiopicpay.features.creditCard.activity

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_CARD_NUMBER
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_CVV
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_EXPIRY_DATE
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_ID
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_NAME
import cesar1287.com.github.desafiopicpay.core.util.Mask.MASK_CREDIT_CARD
import cesar1287.com.github.desafiopicpay.core.util.Mask.MASK_CVV
import cesar1287.com.github.desafiopicpay.core.util.Mask.MASK_EXPIRE_DATE
import cesar1287.com.github.desafiopicpay.core.util.MaskWatcher
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_CREDIT_CARD
import cesar1287.com.github.desafiopicpay.extensions.removeAllWhiteSpaces
import cesar1287.com.github.desafiopicpay.extensions.showToast
import cesar1287.com.github.desafiopicpay.features.BaseActivity
import cesar1287.com.github.desafiopicpay.features.creditCard.viewmodel.CreditCardViewModel
import kotlinx.android.synthetic.main.activity_credit_card.*

class CreditCardActivity : BaseActivity() {

    private var creditCardViewModel: CreditCardViewModel? = null
    private var creditCard: CreditCard? = null
    private var isNumberCreditCardOk = false
    private var isNameOk = false
    private var isExpireDateOk = false
    private var isCvvOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)

        setupObservables()
        setupToolbar(tbCreditCardToolbar)
        setupCreditCard()
    }

    private fun setupCreditCard() {
        creditCard = intent.getParcelableExtra(KEY_EXTRA_CREDIT_CARD)

        creditCard?.let {
            tieCreditCardName.text = Editable.Factory.getInstance().newEditable(it.name)
            tieCreditCardNumber.text = Editable.Factory.getInstance().newEditable(it.cardNumber)
            tieCreditCardExpire.text = Editable.Factory.getInstance().newEditable(it.expiryDate)
            tieCreditCardCvv.text = Editable.Factory.getInstance().newEditable(it.cvv)
        }
    }

    private fun setupObservables() {
        creditCardViewModel = ViewModelProvider(this).get(CreditCardViewModel::class.java)

        creditCardViewModel?.getAllCreditCards()

        tieCreditCardNumber.addTextChangedListener(MaskWatcher(tieCreditCardNumber, MASK_CREDIT_CARD))
        tieCreditCardExpire.addTextChangedListener(MaskWatcher(tieCreditCardExpire, MASK_EXPIRE_DATE))
        tieCreditCardCvv.addTextChangedListener(MaskWatcher(tieCreditCardCvv, MASK_CVV))

        btCreditCardSave.setOnClickListener {
            creditCardViewModel?.save(setupHashMapToSave())
            showToast(getString(R.string.credit_card_save_successfully), Toast.LENGTH_SHORT)
            finish()
        }

        tieCreditCardNumber.doOnTextChanged { text, _, _, after ->
            isNumberCreditCardOk = creditCardViewModel?.isCardNumberOk(text, after) ?: false
            isToShowSaveButton()
        }

        tieCreditCardName.doOnTextChanged { text, _, _, after ->
            isNameOk = creditCardViewModel?.isNameOk(text, after) ?: false
            isToShowSaveButton()
        }

        tieCreditCardExpire.doOnTextChanged { text, _, _, after ->
            isExpireDateOk = creditCardViewModel?.isExpireDateOk(text, after) ?: false
            isToShowSaveButton()
        }

        tieCreditCardCvv.doOnTextChanged { text, _, _, after ->
            isCvvOk = creditCardViewModel?.isCvvOk(text, after) ?: false
            isToShowSaveButton()
        }
    }

    private fun isToShowSaveButton() {
        if (isNumberCreditCardOk && isNameOk && isCvvOk && isExpireDateOk) {
            btCreditCardSave.visibility = View.VISIBLE
            scrollToBottom()
        } else {
            btCreditCardSave.visibility = View.GONE
        }
    }

    private fun scrollToBottom() {
        vgCreditCardParentContainer.postDelayed({
            vgCreditCardParentContainer.smoothScrollTo(tieCreditCardCvv.bottom, btCreditCardSave.bottom)
        }, 300)
    }

    private fun setupHashMapToSave(): HashMap<String, Any?> {
        return hashMapOf(
            KEY_HASH_CARD_NUMBER to tieCreditCardNumber.text.toString().removeAllWhiteSpaces(),
            KEY_HASH_NAME to tieCreditCardName.text.toString(),
            KEY_HASH_EXPIRY_DATE to tieCreditCardExpire.text.toString(),
            KEY_HASH_CVV to tieCreditCardCvv.text.toString(),
            KEY_HASH_ID to creditCard?.id
        )
    }
}
