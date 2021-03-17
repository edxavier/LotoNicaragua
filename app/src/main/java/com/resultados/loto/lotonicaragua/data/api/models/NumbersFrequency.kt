package com.resultados.loto.lotonicaragua.data.api.models

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class NumbersFrequency(
    val numbers: List<Float>,
    val frequency: List<Float>,
    )