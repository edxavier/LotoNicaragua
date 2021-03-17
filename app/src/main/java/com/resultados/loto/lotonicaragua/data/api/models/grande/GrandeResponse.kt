package com.resultados.loto.lotonicaragua.data.api.models.grande
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GrandeResponse(
    var count: Int,
    var next:String? = null,
    var previous:String? = null,
    var results: List<GrandeResult>
)