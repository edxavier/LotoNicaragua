package com.resultados.loto.lotonicaragua.data.api.models.grande

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class GrandeResult(
    val id: Int,

    @Json(name = "draw_time")
    val drawTime: Int,

    @Json(name = "draw_number")
    val drawNumber: Int,

    val number1: String,

    val number2: String,

    val number3: String,

    val number4: String,

    val number5: String,

    @Json(name = "gold_number")
    val gold: String,


    @Json(name = "date_string")
    val dateString: String
)