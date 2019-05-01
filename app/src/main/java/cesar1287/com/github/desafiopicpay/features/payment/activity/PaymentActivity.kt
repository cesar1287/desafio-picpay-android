package cesar1287.com.github.desafiopicpay.features.payment.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.api.Status
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import cesar1287.com.github.desafiopicpay.core.model.TransactionResponse
import cesar1287.com.github.desafiopicpay.core.model.User
import cesar1287.com.github.desafiopicpay.core.util.Error.ERROR_DEFAULT
import cesar1287.com.github.desafiopicpay.core.util.GlideApp
import cesar1287.com.github.desafiopicpay.core.util.Home.KEY_EXTRA_USER
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_CARD_NUMBER
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_CVV
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_DESTINATION_USER_ID
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_EXPIRY_DATE
import cesar1287.com.github.desafiopicpay.core.util.Payment.API_VALUE
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_CREDIT_CARD
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_TRANSACTION
import cesar1287.com.github.desafiopicpay.features.BaseActivity
import cesar1287.com.github.desafiopicpay.features.creditCard.viewmodel.CreditCardViewModel
import cesar1287.com.github.desafiopicpay.features.payment.viewmodel.PaymentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : BaseActivity() {

    private var paymentViewModel : PaymentViewModel? = null
    private var creditCardViewModel : CreditCardViewModel? = null
    private var user : User? = null
    private lateinit var creditCard : CreditCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setupObservables()
        setupToolbar(tbPaymentToolbar)
        setupOnStart()
    }

    private fun setupObservables() {
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        creditCardViewModel = ViewModelProviders.of(this).get(CreditCardViewModel::class.java)

        btPaymentPay.setOnClickListener {
            setupLoadingApiCall(View.VISIBLE)
            val body = setupHashMapToApi()
            paymentViewModel?.insertTransaction(body)
        }

        paymentViewModel?.paymentLiveData?.observe(this, Observer { resource ->
            processApiReturn(resource)
        })
    }

    private fun processApiReturn(resource: Resource?) {
        setupLoadingApiCall(View.GONE)
        when (resource?.status) {
            Status.ERROR -> {
                Snackbar.make(btPaymentPay, resource.message?.let { it } ?: ERROR_DEFAULT, Snackbar.LENGTH_SHORT).show()
            }
            Status.SUCCESS -> {
                startReceiptBottomSheet(resource)
            }
        }
    }

    private fun setupLoadingApiCall(visibility : Int) {
        pbPaymentApiLoading.visibility = visibility
    }

    private fun startReceiptBottomSheet(resource: Resource) {
        val bundle = Bundle().apply {
            putParcelable(KEY_EXTRA_TRANSACTION, resource.data as? TransactionResponse)
            putParcelable(KEY_EXTRA_CREDIT_CARD, creditCard)
        }
        val bottomSheetFragment = ReceiptBottomSheetFragment()
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun setupHashMapToApi(): HashMap<String, Any> {
        return hashMapOf(
            API_CARD_NUMBER to (creditCard.cardNumber ?: "0000"),
            API_CVV to (creditCard.cvv?.toInt() ?: 0),
            API_EXPIRY_DATE to (creditCard.expiryDate ?: "00/00"),
            API_DESTINATION_USER_ID to (user?.id ?: 0),
            API_VALUE to 79.9
        )
    }

    private fun setupOnStart() {
        user = intent.getParcelableExtra(KEY_EXTRA_USER)

        user?.let {
            tvPaymentUsername.text = it.username
            GlideApp.with(this).load(it.img).into(ivPaymentAvatar)
        }

        creditCard = CreditCard().apply {
            cardNumber = "1111111111111112"
            cvv = "789"
            expiryDate = "01/18"
        }
    }
}
