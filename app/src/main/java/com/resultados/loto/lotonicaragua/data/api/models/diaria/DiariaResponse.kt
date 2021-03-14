package com.resultados.loto.lotonicaragua.data.api.models.diaria
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiariaResponse(
    var count: Int,
    var next:String? = null,
    var previous:String? = null,
    var results: List<DiariaResult>
)