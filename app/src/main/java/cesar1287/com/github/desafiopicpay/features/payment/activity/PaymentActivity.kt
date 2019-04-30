package cesar1287.com.github.desafiopicpay.features.payment.activity

import android.os.Bundle
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.model.User
import cesar1287.com.github.desafiopicpay.core.util.GlideApp
import cesar1287.com.github.desafiopicpay.core.util.Home.KEY_EXTRA_USER
import cesar1287.com.github.desafiopicpay.features.BaseActivity
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setupToolbar(tbPaymentToolbar)
        setupUser()
    }

    private fun setupUser() {
        val user = intent.getParcelableExtra<User>(KEY_EXTRA_USER)

        user?.let {
            tvPaymentUsername.text = it.username
            GlideApp.with(this).load(it.img).into(ivPaymentAvatar)
        }
    }
}
