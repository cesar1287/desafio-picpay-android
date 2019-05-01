package cesar1287.com.github.desafiopicpay.core.repository.payment

import cesar1287.com.github.desafiopicpay.core.api.PicpayApi
import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.repository.BaseRepository

class PaymentRepository(private val api : PicpayApi) : BaseRepository() {

    suspend fun insertTransaction(body: HashMap<String, Any>) : Resource {
        return safeApiCall(
            call = { api.insertTransaction(body).await() },
            isTransaction = true
        )
    }
}