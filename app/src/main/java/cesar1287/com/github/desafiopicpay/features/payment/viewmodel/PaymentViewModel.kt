package cesar1287.com.github.desafiopicpay.features.payment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cesar1287.com.github.desafiopicpay.core.api.ApiService
import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.repository.payment.PaymentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PaymentViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : PaymentRepository = PaymentRepository(ApiService.picpayApi)

    val paymentLiveData = MutableLiveData<Resource>()

    fun insertTransaction(body: HashMap<String, Any>){
        scope.launch {
            val transactionResponse = repository.insertTransaction(body)
            paymentLiveData.postValue(transactionResponse)
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}