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

    suspend fun fetchDiaria(resLimit:String = ""):RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val diariaResponse = api.getDiaria(limit = resLimit).await()
            RequestResult.Diaria(diariaResponse.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }
    suspend fun fetchJuega3(resLimit:String = ""):RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val baseResponse = api.getJuega3(limit = resLimit).await()
            RequestResult.Base(baseResponse.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }

    suspend fun fetchTerminacion2(resLimit:String = ""):RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val baseResponse = api.getTerminacion2(limit = resLimit).await()
            RequestResult.Base(baseResponse.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }

    suspend fun fetchFechas(resLimit:String = ""):RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getFechas(limit = resLimit).await()
            RequestResult.Fechas(response.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }

    suspend fun fetchCombo(resLimit:String = ""):RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getCombo(limit = resLimit).await()
            RequestResult.Combo(response.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }
    suspend fun fetchGrande(resLimit:String = ""):RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getLaGrande(limit = resLimit).await()
            RequestResult.Grande(response.results)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }

    suspend fun fetchStatsDiaria():RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getStatsDiaria().await()
            RequestResult.StatsDiaria(response)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }

    suspend fun fetchStatsFechas():RequestResult = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.getStatsFechas().await()
            RequestResult.StatsFecha(response)
        }catch (e: HttpException) {
            RequestResult.Failure(e.code(), e.message())
        }
    }

}