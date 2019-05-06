package cesar1287.com.github.desafiopicpay.features.creditCard.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.features.BaseActivity
import kotlinx.android.synthetic.main.activity_credit_card_cover.*

class CreditCardCoverActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_cover)

        setupToolbar(tbCreditCardCoverToolbar)

        btCoverCreditCardRegister.setOnClickListener {
            startActivity(Intent(this@CreditCardCoverActivity, CreditCardActivity::class.java))
        }
    }
}
