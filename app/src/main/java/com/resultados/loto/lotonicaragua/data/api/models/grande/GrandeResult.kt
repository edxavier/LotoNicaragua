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

    val number1: Int,

    val number2: Int,

    val number3: Int,

    val number4: Int,

    val number5: Int,

    @Json(name = "gold_number")
    val gold: Int,


    @Json(name = "date_string")
    val dateString: String
)