package cesar1287.com.github.desafiopicpay.core.api.callbacks

import cesar1287.com.github.desafiopicpay.core.model.User
import retrofit2.Call
import retrofit2.http.GET

interface PicpayApi {

    @GET("users")
    fun genres(): Call<User>
}
