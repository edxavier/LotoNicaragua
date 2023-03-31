package com.resultados.loto.lotonicaragua.data.api.models.combo

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class ComboResult(
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
    val dateString: String
)