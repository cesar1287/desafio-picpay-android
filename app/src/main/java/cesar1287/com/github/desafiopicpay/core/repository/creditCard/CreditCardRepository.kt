package cesar1287.com.github.desafiopicpay.core.repository.creditCard

import androidx.annotation.WorkerThread
import cesar1287.com.github.desafiopicpay.core.database.CreditCardDao
import cesar1287.com.github.desafiopicpay.core.model.CreditCard

class CreditCardRepository (private val creditCardDao: CreditCardDao){

    @WorkerThread
    fun insert(creditCard: CreditCard) {
        creditCardDao.insert(creditCard)
    }

    @WorkerThread
    fun getAllCreditCards() : List<CreditCard> {
        return creditCardDao.getAll()
    }
}