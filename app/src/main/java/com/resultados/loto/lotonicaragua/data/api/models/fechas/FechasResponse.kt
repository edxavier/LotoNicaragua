package com.resultados.loto.lotonicaragua.data.api.models.fechas
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FechasResponse(
    var count: Int,
    var next:String? = null,
    var previous:String? = null,
    var results: List<FechasResult>
)