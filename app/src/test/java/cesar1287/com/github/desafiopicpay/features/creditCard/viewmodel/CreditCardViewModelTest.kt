package cesar1287.com.github.desafiopicpay.features.creditCard.viewmodel

import android.app.Application
import com.nhaarman.mockitokotlin2.spy
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreditCardViewModelTest {

    lateinit var creditCardViewModel: CreditCardViewModel

    @Before
    fun setUp() {
        val application = Application()
        creditCardViewModel = spy(CreditCardViewModel(application))
    }

    @Test
    fun `verifica se ao passar um cartão ok ele retorna com sucesso`() {
        val returnValue = creditCardViewModel.isCardNumberOk("1111 1111 1111 1111", 1)

        Assert.assertEquals(true, returnValue)
    }

    @Test
    fun `verifica se ao passar um cartão com tamanho menor ele retorna com sucesso`() {
        val returnValue = creditCardViewModel.isCardNumberOk("1111 1111 1111 1", 1)

        Assert.assertEquals(true, returnValue)
    }

    @Test
    fun `verifica o caso em que o usuário excluiu todo o campo cartão de uma vez`() {
        val returnValue = creditCardViewModel.isCardNumberOk("1111 1111 1111 1111", 0)

        Assert.assertEquals(false, returnValue)
    }

    @Test
    fun `verifica se ao passar um nome ok ele retorna com sucesso`() {
        val returnValue = creditCardViewModel.isNameOk("teste", 1)

        Assert.assertEquals(true, returnValue)
    }

    @Test
    fun `verifica se ao passar um nome vazio ele retorna com sucesso`() {
        val returnValue = creditCardViewModel.isNameOk("", 1)

        Assert.assertEquals(false, returnValue)
    }

    @Test
    fun `verifica o caso em que o usuário excluiu todo o campo nome de uma vez`() {
        val returnValue = creditCardViewModel.isNameOk("teste", 0)

        Assert.assertEquals(false, returnValue)
    }
}