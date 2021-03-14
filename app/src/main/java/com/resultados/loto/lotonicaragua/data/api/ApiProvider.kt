package com.resultados.loto.lotonicaragua.data.api

import android.content.Context
import androidx.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

abstract class ApiProvider {
    companion object {
        fun <S> createService(serviceClass: Class<S>, context: Context): S {
            val interceptor = Interceptor {chain ->
                //val token =  prefs.getString("token", "")?:"4c5c1417b29500b1c84b8eac99f965eb6ecf2733"
                val request = chain.request().newBuilder()
                //token.let {
                //   if(it.isNotEmpty())
                //        request.addHeader("Authorization", "Token $token")
                //}
                request.addHeader("Authorization", "Token 4c5c1417b29500b1c84b8eac99f965eb6ecf2733")
                request.addHeader("Accept-Language", Locale.getDefault().language)
                return@Interceptor chain.proceed(request.build())
            }

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(interceptor)

            return Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl("http://64.225.47.75:8080/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                //.addConverterFactory(GsonConverterFactory.create(gsonDatetime))
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(serviceClass)
        }
    }
}