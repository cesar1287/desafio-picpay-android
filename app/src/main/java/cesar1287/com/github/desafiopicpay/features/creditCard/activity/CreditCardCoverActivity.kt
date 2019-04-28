package cesar1287.com.github.desafiopicpay.features.creditCard.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import cesar1287.com.github.desafiopicpay.R
import kotlinx.android.synthetic.main.activity_credit_card_cover.*

class CreditCardCoverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_cover)

        setupToolbar()

        btCoverCreditCardRegister.setOnClickListener {
            startActivity(Intent(this@CreditCardCoverActivity, CreditCardActivity::class.java))
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(tbCreditCardCoverToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
