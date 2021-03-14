package com.resultados.loto.lotonicaragua.data.api.models.combo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComboResponse(
    var count: Int,
    var next:String? = null,
    var previous:String? = null,
    var results: List<ComboResult>
)