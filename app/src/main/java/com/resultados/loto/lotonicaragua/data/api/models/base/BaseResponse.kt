package com.resultados.loto.lotonicaragua.data.api.models.base
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse(
    var count: Int,
    var next:String? = null,
    var previous:String? = null,
    var results: List<BaseResult>
)