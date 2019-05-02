package cesar1287.com.github.desafiopicpay.features.payment.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.api.ApiService
import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.repository.payment.PaymentRepository
import cesar1287.com.github.desafiopicpay.extensions.brlToDouble
import cesar1287.com.github.desafiopicpay.features.BaseViewModel
import kotlinx.coroutines.launch

class PaymentViewModel(application: Application) : BaseViewModel(application) {

    private val repository : PaymentRepository = PaymentRepository(ApiService.picpayApi)

    val paymentLiveData = MutableLiveData<Resource>()

    fun insertTransaction(body: HashMap<String, Any>){
        scope.launch {
            val transactionResponse = repository.insertTransaction(body)
            paymentLiveData.postValue(transactionResponse)
        }
    }

    fun getResourceByValue(text: String, after: Int): Int {
        if (after == 0) {
            return R.drawable.custom_button_disabled
        }

        return if (text.brlToDouble() == 0.0) {
            R.drawable.custom_button_disabled
        } else {
            R.drawable.custom_button
        }
    }
}