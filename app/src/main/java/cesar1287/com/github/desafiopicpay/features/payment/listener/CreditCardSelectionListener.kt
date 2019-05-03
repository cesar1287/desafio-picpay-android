package cesar1287.com.github.desafiopicpay.features.payment.listener

import cesar1287.com.github.desafiopicpay.core.model.CreditCard

interface CreditCardSelectionListener {

    fun onCreditCardSelected(creditCard: CreditCard?)
}