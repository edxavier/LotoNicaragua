package com.resultados.loto.lotonicaragua.data.api

import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResponse
import com.resultados.loto.lotonicaragua.data.api.models.base.BaseResponse
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResponse
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaStats
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechaStats
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResponse
import com.resultados.loto.lotonicaragua.data.api.models.grande.GrandeResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ResultsApiService {
    @GET("/api/diaria/")
    fun getDiaria(@Query("limit") limit:String = ""): Deferred<DiariaResponse>

    @GET("/api/fechas/")
    fun getFechas(@Query("limit") limit:String = ""): Deferred<FechasResponse>

    @GET("/api/juega3/")
    fun getJuega3(@Query("limit") limit:String = ""): Deferred<BaseResponse>

    @GET("/api/supercombo/")
    fun getCombo(@Query("limit") limit:String = ""): Deferred<ComboResponse>

    @GET("/api/terminacion2/")
    fun getTerminacion2(@Query("limit") limit:String = ""): Deferred<BaseResponse>

    @GET("/api/lagrande/")
    fun getLaGrande(@Query("limit") limit:String = ""): Deferred<GrandeResponse>


    @GET("/api/stats/diaria/")
    fun getStatsDiaria(): Deferred<DiariaStats>

    @GET("/api/stats/fechas/")
    fun getStatsFechas(): Deferred<FechaStats>
}