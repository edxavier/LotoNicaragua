package com.resultados.loto.lotonicaragua.data.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserCredentials(
    val username: String,
    val password: String,
    val email: String = ""
)