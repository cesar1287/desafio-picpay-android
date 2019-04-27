package cesar1287.com.github.desafiopicpay.core.api

sealed class ResultApi<out T: Any> {
    data class Success<out T : Any>(val data: T?) : ResultApi<T>()
    data class Error(val exception: Exception) : ResultApi<Nothing>()
}