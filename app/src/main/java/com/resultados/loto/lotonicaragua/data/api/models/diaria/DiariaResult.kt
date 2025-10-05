package com.resultados.loto.lotonicaragua.data.api.models.diaria

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class DiariaResult(
    val id: Int,

    @Json(name = "draw_time")
    val drawTime: Int,

    @Json(name = "draw_number")
    val drawNumber: Int,

    @Json(name = "mas1")
    val plusOne: Int?,

    @Json(name = "winning_number")
    val winningNumber: Int,

    @Json(name = "multix")
    val multiX: String,

    @Json(name = "date_string")
    val dateString: String
)