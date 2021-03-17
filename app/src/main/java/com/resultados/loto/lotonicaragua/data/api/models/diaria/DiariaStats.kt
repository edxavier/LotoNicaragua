package com.resultados.loto.lotonicaragua.data.api.models.diaria

import com.resultados.loto.lotonicaragua.data.api.models.NumbersFrequency
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class DiariaStats(

    val total: Int,
    val histogram: List<Float>,
    @Json(name="numbers_frequency")
    val numbersFrequency: NumbersFrequency
)