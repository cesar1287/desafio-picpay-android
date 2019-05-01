package cesar1287.com.github.desafiopicpay.features.creditCard.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import cesar1287.com.github.desafiopicpay.core.database.PicPayDatabase
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import cesar1287.com.github.desafiopicpay.core.repository.creditCard.CreditCardRepository
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_CARD_NUMBER
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_CVV
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_EXPIRY_DATE
import cesar1287.com.github.desafiopicpay.core.util.CreditCard.KEY_HASH_NAME
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

    fun save(creditCardHashMap: HashMap<String, String>) {
        val creditCard = CreditCard(
            name = creditCardHashMap[KEY_HASH_NAME],
            cardNumber = creditCardHashMap[KEY_HASH_CARD_NUMBER],
            cvv = creditCardHashMap[KEY_HASH_CVV],
            expiryDate = creditCardHashMap[KEY_HASH_EXPIRY_DATE]
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
}