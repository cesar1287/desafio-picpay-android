package cesar1287.com.github.desafiopicpay.features.creditCard.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import cesar1287.com.github.desafiopicpay.core.database.PicPayDatabase
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import cesar1287.com.github.desafiopicpay.core.repository.creditCard.CreditCardRepository
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_CARD_NUMBER
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_CVV
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_EXPIRY_DATE
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_ID
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_NAME
import cesar1287.com.github.desafiopicpay.core.util.Mask.MASK_CREDIT_CARD
import cesar1287.com.github.desafiopicpay.core.util.Mask.MASK_CVV
import cesar1287.com.github.desafiopicpay.core.util.Mask.MASK_EXPIRE_DATE
import cesar1287.com.github.desafiopicpay.extensions.removeAllWhiteSpaces
import cesar1287.com.github.desafiopicpay.features.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreditCardViewModel(application: Application) : BaseViewModel(application) {

    private val repository: CreditCardRepository

    var allCreditCards: MutableLiveData<List<CreditCard>> = MutableLiveData()

    init {
        val creditCardDao = PicPayDatabase.getDatabase(application).creditCardDao()

        repository = CreditCardRepository(creditCardDao)
    }

    fun save(creditCardHashMap: HashMap<String, Any?>) {
        val creditCard = CreditCard(
            name = creditCardHashMap[KEY_HASH_NAME] as? String,
            cardNumber = creditCardHashMap[KEY_HASH_CARD_NUMBER] as? String,
            cvv = creditCardHashMap[KEY_HASH_CVV] as? String,
            expiryDate = creditCardHashMap[KEY_HASH_EXPIRY_DATE] as? String,
            id = creditCardHashMap[KEY_HASH_ID] as? Int
        )

        insert(creditCard)
    }

    private fun insert(creditCard: CreditCard) = scope.launch(Dispatchers.IO) {
        repository.insert(creditCard)
    }

    fun getAllCreditCards() {
        scope.launch(Dispatchers.IO) {
            allCreditCards.postValue(repository.getAllCreditCards())
        }
    }

    fun isCardNumberOk(text: CharSequence?, after: Int): Boolean {
        if (after == 0) {
            return false
        }

        val inputTextLength = text.toString().length
        val maskLength = MASK_CREDIT_CARD.removeAllWhiteSpaces().length

        if (inputTextLength > maskLength) {
            return true
        }

        return inputTextLength == maskLength
    }

    fun isNameOk(text: CharSequence?, after: Int): Boolean {
        if (after == 0) {
            return false
        }

        return text.toString().isNotBlank()
    }

    fun isExpireDateOk(text: CharSequence?, after: Int): Boolean? {
        if (after == 0) {
            return false
        }

        val inputTextLength = text.toString().length
        val maskLength = MASK_EXPIRE_DATE.replace("/", "").length

        if (inputTextLength > maskLength) {
            return true
        }

        return inputTextLength == maskLength || inputTextLength == (maskLength - 1)
    }

    fun isCvvOk(text: CharSequence?, after: Int): Boolean? {
        if (after == 0) {
            return false
        }

        val inputTextLength = text.toString().length
        val maskLength = MASK_CVV.length

        if (inputTextLength > maskLength) {
            return true
        }

        return inputTextLength == maskLength
    }
}