package cesar1287.com.github.desafiopicpay.core.api.repository.home

import cesar1287.com.github.desafiopicpay.core.api.callbacks.PicpayApi
import cesar1287.com.github.desafiopicpay.core.api.repository.BaseRepository
import cesar1287.com.github.desafiopicpay.core.model.User

class UserRepository(private val api : PicpayApi) : BaseRepository() {

    suspend fun getUsers() : MutableList<User>?{
        val usersResponse = safeApiCall(
            call = {api.users().await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        return usersResponse?.toMutableList()
    }

}