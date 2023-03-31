package com.resultados.loto.lotonicaragua.data.api.models.diaria

import com.resultados.loto.lotonicaragua.data.api.models.NumbersFrequency
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class DiariaStats(

    val total: Int,
    @Json(name="histogram_freq")
    val histogram: List<Int>,
    @Json(name="frequency")
    val numbersFrequency:List<NumbersFrequency>
)