package com.resultados.loto.lotonicaragua.data.api

import com.resultados.loto.lotonicaragua.data.api.models.Token
import com.resultados.loto.lotonicaragua.data.api.models.UserCredentials
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountApiService {
    @POST("/rest-auth/login/")
    fun login(@Body credentials: UserCredentials): Deferred<Token>
}