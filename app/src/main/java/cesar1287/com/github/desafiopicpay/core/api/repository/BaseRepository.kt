package cesar1287.com.github.desafiopicpay.core.api.repository

import android.util.Log
import cesar1287.com.github.desafiopicpay.core.api.ResultApi
import retrofit2.Response
import java.io.IOException

open class BaseRepository{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        val result : ResultApi<T> = safeApiResult(call,errorMessage)

        var data : T? = null

        when(result) {
            is ResultApi.Success ->
                data = result.data
            is ResultApi.Error -> {
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
            }
        }

        return data
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>, errorMessage: String) : ResultApi<T>{
        val response = call.invoke()

        return if(response.isSuccessful) {
            ResultApi.Success(response.body())
        } else {
            ResultApi.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
        }
    }
}