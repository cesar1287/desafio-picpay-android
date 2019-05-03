package cesar1287.com.github.desafiopicpay.features.payment.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.api.Status
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import cesar1287.com.github.desafiopicpay.core.model.TransactionResponse
import cesar1287.com.github.desafiopicpay.core.model.User
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_EXTRA_CREDIT_CARD_LIST
import cesar1287.com.github.desafiopicpay.core.util.Error.ERROR_DEFAULT
import cesar1287.com.github.desafiopicpay.core.util.GlideApp
import cesar1287.com.github.desafiopicpay.core.util.Home.KEY_EXTRA_USER
import cesar1287.com.github.desafiopicpay.core.util.MoneyTextWatcher
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_CARD_NUMBER
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_CVV
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_DESTINATION_USER_ID
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_EXPIRY_DATE
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_VALUE
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_CREDIT_CARD
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_TRANSACTION
import cesar1287.com.github.desafiopicpay.extensions.brlToDouble
import cesar1287.com.github.desafiopicpay.extensions.getLast4CreditCardNumbers
import cesar1287.com.github.desafiopicpay.extensions.showSnackBar
import cesar1287.com.github.desafiopicpay.features.BaseActivity
import cesar1287.com.github.desafiopicpay.features.creditCard.activity.CreditCardActivity
import cesar1287.com.github.desafiopicpay.features.creditCard.activity.CreditCardCoverActivity
import cesar1287.com.github.desafiopicpay.features.creditCard.viewmodel.CreditCardViewModel
import cesar1287.com.github.desafiopicpay.features.payment.viewmodel.PaymentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_payment.*
import java.lang.ref.WeakReference

class PaymentActivity : BaseActivity() {

    private var paymentViewModel : PaymentViewModel? = null
    private var creditCardViewModel : CreditCardViewModel? = null
    private var user : User? = null
    private lateinit var creditCard : CreditCard
    private val creditCardList: MutableList<CreditCard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setupToolbar(tbPaymentToolbar)
        setupViewModel()
        setupObservables()
    }

    override fun onResume() {
        super.onResume()

        setupOnStart()
    }

    private fun processApiReturn(resource: Resource?) {
        setupLoadingApiCall(View.GONE)
        when (resource?.status) {
            Status.ERROR -> {
                btPaymentPay.showSnackBar(this, resource.message?.let { it } ?: ERROR_DEFAULT, Snackbar.LENGTH_SHORT)
            }
            Status.SUCCESS -> {
                startReceiptBottomSheet(resource)
            }
        }
    }

    private fun processCreditCarList(it: List<CreditCard>?) {
        it?.let { creditCardList ->
            creditCardList.firstOrNull()?.let {
                setupSelectedCreditCard(it)
            } ?: run {
                setVisibilityContainerSelectedCreditCard(View.GONE)
            }
        }
    }

    private fun setupSelectedCreditCard(it: CreditCard) {
        setupFirstCreditCard(it)

        setVisibilityContainerSelectedCreditCard(View.VISIBLE)
        tvPaymentCreditCard.text = String.format(getString(R.string.payment_selected_credit_card),
            creditCard.cardNumber?.getLast4CreditCardNumbers())
    }

    private fun setupFirstCreditCard(it: CreditCard) {
        creditCard = CreditCard().apply {
            cardNumber = it.cardNumber
            cvv = it.cvv
            expiryDate = it.expiryDate
            name = it.name
            id = it.id
        }
    }

    private fun setVisibilityContainerSelectedCreditCard(visibility: Int) {
        tvPaymentCreditCard.visibility = visibility
        tvPaymentEdit.visibility = visibility
    }

    private fun setupLoadingApiCall(visibility : Int) {
        pbPaymentApiLoading.visibility = visibility
    }

    private fun startReceiptBottomSheet(resource: Resource) {
        setResult(Activity.RESULT_OK, createReturnToMainIntent(resource))
        finish()
    }

    private fun createReturnToMainIntent(resource: Resource): Intent {
        return Intent().apply {
            putExtra(KEY_EXTRA_TRANSACTION, resource.data as? TransactionResponse)
            putExtra(KEY_EXTRA_CREDIT_CARD, creditCard)
        }
    }

    private fun setupHashMapToApi(): HashMap<String, Any> {
        return hashMapOf(
            API_CARD_NUMBER to (creditCard.cardNumber ?: "0000"),
            API_CVV to (creditCard.cvv?.toInt() ?: 0),
            API_EXPIRY_DATE to (creditCard.expiryDate ?: "00/00"),
            API_DESTINATION_USER_ID to (user?.id ?: 0),
            API_VALUE to etPaymentValue.text.toString().brlToDouble()
        )
    }

    private fun setupViewModel() {
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        creditCardViewModel = ViewModelProviders.of(this).get(CreditCardViewModel::class.java)
    }

    private fun setupObservables() {
        componentObservables()

        viewModelObservables()
    }

    private fun viewModelObservables() {
        paymentViewModel?.paymentLiveData?.observe(this, Observer { resource ->
            processApiReturn(resource)
        })

        creditCardViewModel?.allCreditCards?.observe(this, Observer {
            creditCardList.clear()
            creditCardList.addAll(it)
            processCreditCarList(it)
        })

        paymentViewModel?.creditCardSelected?.observe(this, Observer { creditCard ->
            creditCard?.let { setupSelectedCreditCard(it) }
        })
    }

    private fun componentObservables() {
        etPaymentValue.addTextChangedListener(MoneyTextWatcher(WeakReference(etPaymentValue)))

        etPaymentValue.doOnTextChanged { text, _, _, after ->
            setupEditValueContainer(text, after)
        }

        btPaymentPay.setOnClickListener {
            onClickPaymentButton()
        }

        btPaymentRegisterCreditCard.setOnClickListener {
            startCreditCardCover()
        }

        tvPaymentCreditCard.setOnClickListener {
            startCreditCardSelectionBottomSheet()
        }

        tvPaymentEdit.setOnClickListener {
            startEditCreditCard()
        }
    }

    private fun startEditCreditCard() {
        val intent = Intent(this@PaymentActivity, CreditCardActivity::class.java)
        intent.putExtra(KEY_EXTRA_CREDIT_CARD, creditCard)
        startActivity(intent)
    }

    private fun onClickPaymentButton() {
        if (verifyHasCreditCard()) {
            setupLoadingApiCall(View.VISIBLE)
            val body = setupHashMapToApi()
            paymentViewModel?.insertTransaction(body)
        } else {
            startCreditCardCover()
        }
    }

    private fun startCreditCardSelectionBottomSheet() {
        val bottomSheetFragment = CreditCardBottomSheetFragment()
        bottomSheetFragment.arguments = createBundleBottomSheet(creditCardList)
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun setupEditValueContainer(text: CharSequence?, after: Int) {
        val buttonBackground = paymentViewModel?.getResourceByValue(text.toString(), after) ?: R.drawable.custom_button

        if (buttonBackground == R.drawable.custom_button) {
            setupPaymentScreen(
                textColor = ContextCompat.getColor(this, R.color.colorAccent),
                isButtonEnabled = true,
                alpha = 1f
            )
        } else {
            setupPaymentScreen(
                textColor = ContextCompat.getColor(this, R.color.title_white),
                isButtonEnabled = false,
                alpha = 0.4f
            )
        }

        btPaymentPay.setBackgroundResource(buttonBackground)
    }

    private fun createBundleBottomSheet(creditCardList: MutableList<CreditCard>): Bundle? {
        return Bundle().apply {
            putParcelableArray(KEY_EXTRA_CREDIT_CARD_LIST, creditCardList.toTypedArray())
        }
    }

    private fun startCreditCardCover() {
        startActivity(Intent(this@PaymentActivity, CreditCardCoverActivity::class.java))
    }

    private fun verifyHasCreditCard(): Boolean {
        return !creditCardList.isNullOrEmpty()
    }

    private fun setupPaymentScreen(textColor: Int, isButtonEnabled: Boolean, alpha: Float) {
        tvPaymentMonetaryMask.setTextColor(textColor)
        tvPaymentMonetaryMask.alpha = alpha
        etPaymentValue.setTextColor(textColor)
        etPaymentValue.alpha = alpha
        btPaymentPay.isEnabled = isButtonEnabled
    }

    private fun setupOnStart() {
        user = intent.getParcelableExtra(KEY_EXTRA_USER)

        user?.let {
            tvPaymentUsername.text = it.username
            GlideApp.with(this).load(it.img).into(ivPaymentAvatar)
        }

        creditCardViewModel?.getAllCreditCards()
    }
}
