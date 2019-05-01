package cesar1287.com.github.desafiopicpay.core.repository

import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.model.TransactionResponse
import cesar1287.com.github.desafiopicpay.core.util.Error.ERROR_DEFAULT
import cesar1287.com.github.desafiopicpay.core.util.ErrorUtils
import retrofit2.Response
import java.lang.Exception

open class BaseRepository{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, isTransaction: Boolean? = null): Resource {
        return if (isTransaction == true) {
            safeApiResultTransaction(call)
        } else {
            safeApiResult(call)
        }
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>) : Resource{
        try {
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
        } catch (error : Exception) {
            return Resource.error(ERROR_DEFAULT)
        }
    }

    private suspend fun <T: Any> safeApiResultTransaction(call: suspend ()-> Response<T>) : Resource{
        try {
            val response = call.invoke()

            return if(response.isSuccessful) {
                val transactionResponse = response.body() as? TransactionResponse

                transactionResponse?.let {
                    if (it.transaction.success) {
                        Resource.success(response.body())
                    } else {
                        Resource.error("Transação não autorizada, tente novamente")
                    }
                } ?: run {
                    Resource.error(ERROR_DEFAULT)
                }
            } else {
                val error = ErrorUtils.parseError(response)

                error?.message?.let {  message ->
                    Resource.error(message)
                } ?: run {
                    Resource.error(ERROR_DEFAULT)
                }
            }
        } catch (error : Exception) {
            return Resource.error(ERROR_DEFAULT)
        }
    }
}