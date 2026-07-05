package com.resultados.loto.lotonicaragua.data.api.models.juega4

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Juega4Response(
    var count: Int,
    var next: String? = null,
    var previous: String? = null,
    var results: List<Juega4Result>,
)
