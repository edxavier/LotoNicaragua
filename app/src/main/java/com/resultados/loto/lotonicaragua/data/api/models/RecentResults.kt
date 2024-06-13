package com.resultados.loto.lotonicaragua.data.api.models

import com.resultados.loto.lotonicaragua.data.api.models.base.BaseResult
import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResult
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResult
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecentResults (
    @Json
    val diaria: List<DiariaResult> ,
    @Json
    val fechas: List<FechasResult>,
    @Json
    val juega3: List<BaseResult>,
    @Json
    val premia2: List<ComboResult>,
    @Json
    val terminacion: List<BaseResult> ,
)