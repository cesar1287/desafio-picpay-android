package cesar1287.com.github.desafiopicpay.core.api

import cesar1287.com.github.desafiopicpay.core.api.callbacks.PicpayApi
import cesar1287.com.github.desafiopicpay.core.util.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    val picpayApi: PicpayApi = getPicpayApiClient().create(PicpayApi::class.java)

    fun getPicpayApiClient() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}