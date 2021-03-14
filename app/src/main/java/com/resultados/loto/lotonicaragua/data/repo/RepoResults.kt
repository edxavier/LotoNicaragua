package com.resultados.loto.lotonicaragua.data.repo

import android.content.Context
import android.util.Log
import com.resultados.loto.lotonicaragua.data.RequestResult
import com.resultados.loto.lotonicaragua.data.api.ApiProvider
import com.resultados.loto.lotonicaragua.data.api.ResultsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*


class RepoResults(private val context: Context){

    private val api =  ApiProvider.createService(ResultsApiService::class.java, context)

    suspend fun fetchDiaria():RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val diariaResponse = api.getDiaria().await()
            RequestResult.Diaria(diariaResponse.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }
    suspend fun fetchJuega3():RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val diariaResponse = api.getJuega3().await()
            RequestResult.Diaria(diariaResponse.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }

    suspend fun fetchTerminacion2():RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val diariaResponse = api.getTerminacion2().await()
            RequestResult.Diaria(diariaResponse.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }

    suspend fun fetchFechas():RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getFechas().await()
            RequestResult.Fechas(response.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }

    suspend fun fetchCombo():RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getCombo().await()
            RequestResult.Combo(response.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }
}