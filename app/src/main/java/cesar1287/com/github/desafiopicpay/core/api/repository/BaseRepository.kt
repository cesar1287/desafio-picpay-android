package cesar1287.com.github.desafiopicpay.core.api.repository

import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.util.ERROR_DEFAULT
import cesar1287.com.github.desafiopicpay.core.util.ErrorUtils
import retrofit2.Response

open class BaseRepository{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource {
        return safeApiResult(call)
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>) : Resource{
        val response = call.invoke()

        return if(response.isSuccessful) {
            Resource.success(response.body())
        } else {
            val error = ErrorUtils.parseError(response)

            error?.message?.let {  message ->
                Resource.error(message)
            } ?: run {
                Resource.error(ERROR_DEFAULT)
            }
        }
    }
}