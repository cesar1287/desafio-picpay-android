package cesar1287.com.github.desafiopicpay.core.api

import cesar1287.com.github.desafiopicpay.core.model.TransactionResponse
import cesar1287.com.github.desafiopicpay.core.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PicpayApi {

    @GET("users")
    fun users(): Deferred<Response<List<User>>>

    @POST("transaction")
    fun insertTransaction(@Body body: HashMap<String, Any>): Deferred<Response<TransactionResponse>>
}
