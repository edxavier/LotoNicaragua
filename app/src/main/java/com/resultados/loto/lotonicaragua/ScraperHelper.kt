package com.resultados.loto.lotonicaragua

import android.util.Log
import com.resultados.loto.lotonicaragua.data.LotoResult
import com.resultados.loto.lotonicaragua.data.api.models.base.BaseResult
import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResult
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResult
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import com.resultados.loto.lotonicaragua.data.api.models.grande.GrandeResult
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
        //Log.e("EDER", entries.toString())
        results.forEach { e ->
            //Log.e("EDER", e.dateString)
            val fecha = e.dateString
            val hora = e.dateString.split("|")[1]
            entries.add(
                LotoResult(
                    date = fecha, code = e.drawNumber.toString(), time = hora,
                    result1 = e.winningNumber, multix = e.multiX
                )
            )
        }
        return entries
    }

    fun apiBase(results: List<BaseResult>): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        results.forEach { e ->
            val fecha = e.dateString
            val hora = e.dateString.split("|")[1]
            entries.add(LotoResult(date = fecha, code = e.drawNumber.toString(), time = hora, result1 = e.winningNumber))
        }
        return entries
    }

    fun apiFechas(results: List<FechasResult>): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        results.forEach { e ->
            val fecha = e.dateString
            val hora = e.dateString.split("|")[1]
            entries.add(
                LotoResult(
                    date = fecha, code = e.drawNumber.toString(),
                    time = hora, result1 = e.winningNumber, month = e.winningMonth,
                    game = FECHAS
                    )
            )
        }
        return entries
    }

    fun apiCombo(results: List<ComboResult>): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        results.forEach { e ->
            val fecha = e.dateString
            val hora = e.dateString.split("|")[1]
            entries.add(
                LotoResult(date = fecha, code = e.drawNumber.toString(),
                time = hora, result1 = e.winningNumber1, result2 = e.winningNumber2, game = SUPERCOMBO
                )
            )
        }
        return entries
    }

    fun apiGrande(results: List<GrandeResult>): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        results.forEach { e ->
            val fecha = e.dateString
            val hora = e.dateString.split("|")[1]
            entries.add(
                LotoResult(date = fecha, code = e.drawNumber.toString(),
                    time = hora, result1 = e.number1, result2 = e.number2,
                    result3 = e.number3, result4 = e.number4,
                    result5 = e.number5, result6 = e.gold, game = LAGRANDE
                )
            )
        }
        return entries
    }
}