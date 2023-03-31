package com.resultados.loto.lotonicaragua.data.api.models

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class NumbersFrequency(
    val number: Int,
    val frequency: Int,
    )

@JsonClass(generateAdapter = true)
data class FechaFrequency(
    val number: Int,
    val freq: Int,
)