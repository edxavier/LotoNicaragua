package com.resultados.loto.lotonicaragua.data.api.models.fechas

import com.resultados.loto.lotonicaragua.data.api.models.FechaFrequency
import com.resultados.loto.lotonicaragua.data.api.models.NumbersFrequency
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class FechaStats(

    val total: Int,
    @Json(name="histogram_freq")
    val histogram: List<Float>,

    @Json(name="month_histogram_freq")
    val monthHistogram: List<Float>,

    @Json(name="numbers_freq_count")
    val numbersFrequency: List<FechaFrequency>,

    @Json(name="top_mixin")
    val topMixin: List<TopMix>

)