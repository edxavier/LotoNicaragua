package com.resultados.loto.lotonicaragua.data.api.models.base

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class BaseResult(
    val id: Int,

    @Json(name = "draw_time")
    val drawTime: Int,

    @Json(name = "draw_number")
    val drawNumber: Int,

    @Json(name = "winning_number")
    val winningNumber: String,

    @Json(name = "date_string")
    val dateString: String
)