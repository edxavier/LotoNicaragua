package com.resultados.loto.lotonicaragua.data.api

import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResponse
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResponse
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ResultsApiService {
    @GET("/api/diaria/")
    fun getDiaria(): Deferred<DiariaResponse>

    @GET("/api/fechas/")
    fun getFechas(): Deferred<FechasResponse>

    @GET("/api/juega3/")
    fun getJuega3(): Deferred<DiariaResponse>

    @GET("/api/supercombo/")
    fun getCombo(): Deferred<ComboResponse>

    @GET("/api/terminacion2/")
    fun getTerminacion2(): Deferred<DiariaResponse>

    @GET("/api/lagrande/")
    fun getLaGrande(): Deferred<DiariaResponse>
}