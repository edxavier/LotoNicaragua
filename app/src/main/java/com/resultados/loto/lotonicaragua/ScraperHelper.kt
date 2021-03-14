package com.resultados.loto.lotonicaragua

import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResult
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResult
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import org.jsoup.nodes.Document
import java.util.ArrayList

object ScraperHelper {

    val DIARIA = 0
    val FECHAS = 1
    val JUEGA3 = 2
    val SUPERCOMBO = 3
    val LAGRANDE = 4
    val TERMINACION2 = 5


    fun apiDiaria(results: List<DiariaResult>): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        results.forEach { e ->
            val fecha = e.dateString.split("|")[0]
            val hora = e.dateString.split("|")[1]
            entries.add(LotoResult(date = fecha, code = e.drawNumber.toString(), time = hora, result1 = e.winningNumber.toString()))
        }
        return entries
    }

    fun apiFechas(results: List<FechasResult>): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        results.forEach { e ->
            val fecha = e.dateString.split("|")[0]
            val hora = e.dateString.split("|")[1]
            entries.add(LotoResult(date = fecha, code = e.drawNumber.toString(),
                time = hora, result1 = e.winningNumber.toString(), result2 = e.winningMonth))
        }
        return entries
    }

    fun apiCombo(results: List<ComboResult>): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        results.forEach { e ->
            val fecha = e.dateString.split("|")[0]
            val hora = e.dateString.split("|")[1]
            entries.add(LotoResult(date = fecha, code = e.drawNumber.toString(),
                time = hora, result1 = e.winningNumber1.toString(), result2 = e.winningNumber2))
        }
        return entries
    }

}