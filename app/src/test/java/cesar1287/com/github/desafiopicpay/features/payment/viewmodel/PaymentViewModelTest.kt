package cesar1287.com.github.desafiopicpay.features.payment.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import com.nhaarman.mockitokotlin2.spy
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

class PaymentViewModelTest {

    lateinit var paymentViewModel: PaymentViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val application = Application()
        paymentViewModel = spy(PaymentViewModel(application))
    }

    @Test
    fun `verifica se resource é passado corretamente em caso de valor diferente de 0,0`() {
        val expectedValue = paymentViewModel.getResourceByValue("0,2", 1)

        assertEquals(R.drawable.custom_button, expectedValue)
    }

    @Test
    fun `verifica se resource é passado corretamente em caso de valor ser 0,0`() {
        val expectedValue = paymentViewModel.getResourceByValue("0,0", 1)

        assertEquals(R.drawable.custom_button_disabled, expectedValue)
    }

    @Test
    fun `verifica se resource é passado corretamente caso o usuário exclua todo o valor de uma vez`() {
        val expectedValue = paymentViewModel.getResourceByValue("22,2", 0)

        assertEquals(R.drawable.custom_button_disabled, expectedValue)
    }

    @Test
    fun `verifica se é setado corretamente o cartão selecinado no livedata`() {
        val creditCard = CreditCard().apply {
            cardNumber = "1111111111111111"
            expiryDate = "01/19"
            name = "teste"
            cvv = "123"
            id = 1
        }

        paymentViewModel.setCreditCardSelected(creditCard)

        assertEquals(paymentViewModel.creditCardSelected.value, creditCard)
    }
}