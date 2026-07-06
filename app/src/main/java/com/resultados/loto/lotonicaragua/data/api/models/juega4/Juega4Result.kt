package com.resultados.loto.lotonicaragua.data.api.models.juega4

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Juega4Result(
    val id: Int,

    @Json(name = "draw_time")
    val drawTime: Int,

    @Json(name = "draw_number")
    val drawNumber: Int,

    @Json(name = "winning_number_1")
    val winningNumber1: Int,

    @Json(name = "winning_number_2")
    val winningNumber2: Int,

    @Json(name = "date_string")
    val dateString: String,

    val created: String?,
    val updated: String?,
)
