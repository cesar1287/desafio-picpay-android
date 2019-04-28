package cesar1287.com.github.desafiopicpay.core.api.repository.home

import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.api.callbacks.PicpayApi
import cesar1287.com.github.desafiopicpay.core.api.repository.BaseRepository

class UserRepository(private val api : PicpayApi) : BaseRepository() {

    suspend fun getUsers() : Resource{
        return safeApiCall(
            call = { api.users().await() }
        )
    }

}