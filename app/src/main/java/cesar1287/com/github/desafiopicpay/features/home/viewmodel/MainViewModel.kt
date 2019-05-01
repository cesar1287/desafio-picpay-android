package cesar1287.com.github.desafiopicpay.features.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import cesar1287.com.github.desafiopicpay.core.api.ApiService
import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.repository.home.UserRepository
import cesar1287.com.github.desafiopicpay.features.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application){

    private val repository : UserRepository = UserRepository(ApiService.picpayApi)

    val usersLiveData = MutableLiveData<Resource>()

    fun fetchUsers(){
        scope.launch {
            val users = repository.getUsers()
            usersLiveData.postValue(users)
        }
    }
}