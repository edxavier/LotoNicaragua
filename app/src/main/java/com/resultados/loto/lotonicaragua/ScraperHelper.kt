package com.resultados.loto.lotonicaragua

import android.util.Log
import org.jsoup.nodes.Document
import java.util.ArrayList

object ScraperHelper {

    val DIARIA = 0
    val FECHAS = 1
    val JUEGA3 = 2
    val SUPERCOMBO = 3
    val LAGRANDE = 4
    val TERMINACION2 = 5


    fun scrapDiaria(document:Document): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        val data = document.getElementsByClass("resultadosdiaria").select("table>tbody>tr")
        data.removeAt(0)
        data.forEach { element ->
            val col = element.getElementsByTag("tr").select("td")
            if(col.size==4){
                entries.add(LotoResult(date = col[0].text(), code = col[1].text(), time = col[2].text(), result1 = col[3].text()))
            }
        }
        return entries.asReversed()
    }

    fun scrapJuega3(document:Document): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        val data = document.getElementsByClass("resultadosjuga3").select("table>tbody>tr")
        data.removeAt(0)
        data.forEach { element ->
            val col = element.getElementsByTag("tr").select("td")
            if(col.size==4){
                entries.add(LotoResult(date = col[0].text(), code = col[1].text(), time = col[2].text(), result1 = col[3].text()))
            }
        }
        return entries.asReversed()
    }

    fun scrapFechas(document:Document): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        val data = document.getElementsByClass("resultadosfechas").select("table>tbody>tr")
        data.removeAt(0)
        data.forEach { element ->
            val col = element.getElementsByTag("tr").select("td")
            if(col.size==5){
                val mes = col[4].text().subSequence(0,3)
                entries.add(LotoResult(date = col[0].text(), code = col[1].text(), time = col[2].text(), result1 = col[3].text(), result2 = mes.toString()))
            }
        }
        return entries.asReversed()
    }

    fun scrapSuperCombo(document:Document): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        val data = document.getElementsByClass("resultadoscombo").select("table>tbody>tr")
        data.removeAt(0)
        data.forEach { element ->
            val col = element.getElementsByTag("tr").select("td")
            if(col.size==5){
                entries.add(LotoResult(date = col[0].text(), code = col[1].text(), time = col[2].text(), result1 = col[3].text(), result2 = col[4].text()))
            }
        }
        return entries.asReversed()
    }

    fun scrapTerminacion2(document:Document): List<LotoResult>{
        val entries: MutableList<LotoResult> = ArrayList()
        val data = document.getElementsByClass("resultadost2").select("table>tbody>tr")
        data.removeAt(0)
        data.forEach { element ->
            val col = element.getElementsByTag("tr").select("td")
            if(col.size==3){
                entries.add(LotoResult(date = col[0].text(), code = col[1].text(),  result1 = col[2].text()))
            }
        }
        return entries.asReversed()
    }

}