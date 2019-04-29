package cesar1287.com.github.desafiopicpay.features.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cesar1287.com.github.desafiopicpay.core.api.ApiService
import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.repository.home.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(){

    private val parentJob = SupervisorJob()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repository : UserRepository = UserRepository(ApiService.picpayApi)

    val usersLiveData = MutableLiveData<Resource>()

    fun fetchUsers(){
        scope.launch {
            val users = repository.getUsers()
            usersLiveData.postValue(users)
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}