package cesar1287.com.github.desafiopicpay.features.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import cesar1287.com.github.desafiopicpay.core.api.ApiService
import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.api.Status
import cesar1287.com.github.desafiopicpay.core.model.User
import cesar1287.com.github.desafiopicpay.core.repository.home.UserRepository
import cesar1287.com.github.desafiopicpay.features.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application){

    private val repository : UserRepository = UserRepository(ApiService.picpayApi)

    val usersLiveDataSuccess = MutableLiveData<Resource?>()
    val errorMessage = MutableLiveData<Resource?>()

    fun fetchUsers(){
        scope.launch {
            val users = repository.getUsers()

            when (users?.status) {
                Status.ERROR -> {
                    errorMessage.postValue(users)
                }
                Status.SUCCESS -> {
                    usersLiveDataSuccess.postValue(users)
                }
            }

        }
    }

    fun doSearch(usersList: List<User>, text: CharSequence?, after: Int): List<User> {
        if (after == 0) {
            return usersList
        }

        return usersList.filter { it.username.contains(text.toString(), ignoreCase = true) }
    }
}