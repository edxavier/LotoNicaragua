package com.resultados.loto.lotonicaragua.data.api.models.fechas

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class TopMix(
    val month: String,
    val day: String,
    val count: String,
    )