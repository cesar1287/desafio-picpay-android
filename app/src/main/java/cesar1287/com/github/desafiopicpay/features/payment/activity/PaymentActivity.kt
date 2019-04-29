package cesar1287.com.github.desafiopicpay.features.payment.activity

import android.os.Bundle
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.features.BaseActivity
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setupToolbar(tbPaymentToolbar)
    }
}
