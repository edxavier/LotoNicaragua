package com.resultados.loto.lotonicaragua.data

import com.resultados.loto.lotonicaragua.data.api.models.base.BaseResult
import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResult
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResult
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaStats
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechaStats
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import com.resultados.loto.lotonicaragua.data.api.models.grande.GrandeResult

sealed class RequestResult {
    data class Failure(val status:Int, val text:String) : RequestResult()
    data class Base(val results: List<BaseResult>):RequestResult()
    data class Diaria(val results: List<DiariaResult>):RequestResult()
    data class Fechas(val results: List<FechasResult>):RequestResult()
    data class Combo(val results: List<ComboResult>):RequestResult()
    data class Grande(val results: List<GrandeResult>):RequestResult()

    data class StatsDiaria(val stats: DiariaStats):RequestResult()
    data class StatsFecha(val stats: FechaStats):RequestResult()
}