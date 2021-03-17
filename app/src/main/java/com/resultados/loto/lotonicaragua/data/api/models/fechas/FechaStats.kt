package com.resultados.loto.lotonicaragua.data.api.models.fechas

import com.resultados.loto.lotonicaragua.data.api.models.NumbersFrequency
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class FechaStats(

    val total: Int,
    val histogram: List<Float>,

    @Json(name="month_histogram")
    val monthHistogram: List<Float>,

    @Json(name="numbers_frequency")
    val numbersFrequency: NumbersFrequency,

    @Json(name="top_mixin")
    val topMixin: List<TopMix>

)