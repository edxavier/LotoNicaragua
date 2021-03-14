package com.resultados.loto.lotonicaragua.data

import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResult
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResult
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult

sealed class RequestResult {
    data class Failure(val status:Int, val text:String) : RequestResult()
    data class Diaria(val results: List<DiariaResult>):RequestResult()
    data class Fechas(val results: List<FechasResult>):RequestResult()
    data class Combo(val results: List<ComboResult>):RequestResult()
}