package cesar1287.com.github.desafiopicpay.core.api

import cesar1287.com.github.desafiopicpay.core.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface PicpayApi {

    @GET("users")
    fun users(): Deferred<Response<List<User>>>
}
