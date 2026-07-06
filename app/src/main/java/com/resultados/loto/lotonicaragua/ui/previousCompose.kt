package com.resultados.loto.lotonicaragua.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.LotoResult
import com.resultados.loto.lotonicaragua.ui.home.composes.CardTopAccent
import com.resultados.loto.lotonicaragua.ui.home.composes.ResultBall

@Composable
fun PreviousResults(results: List<LotoResult>) {
    LazyColumn(
        modifier = Modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) { items(results) { PreviousResult(it) } }
}

@Composable
fun PreviousResult(result: LotoResult) {
    val accent = when (result.game) {
        ScraperHelper.LAGRANDE -> accentGrande
        ScraperHelper.FECHAS -> accentFechas
        ScraperHelper.JUEGA3 -> accentJuega3
        ScraperHelper.JUEGA4 -> accentJuega4
        ScraperHelper.SUPERCOMBO -> accentCombo
        ScraperHelper.TERMINACION2 -> accentTerminacion
        else -> accentDiaria
    }
    val pastel = when (result.game) {
        ScraperHelper.LAGRANDE -> pastelGrande
        ScraperHelper.FECHAS -> pastelFechas
        ScraperHelper.JUEGA3 -> pastelJuega3
        ScraperHelper.JUEGA4 -> pastelJuega4
        ScraperHelper.SUPERCOMBO -> pastelCombo
        ScraperHelper.TERMINACION2 -> pastelTerminacion
        else -> pastelDiaria
    }

    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = pastel)
    ) {
        Column {
            CardTopAccent(accent)
            Column(Modifier.padding(18.dp)) {
                if (result.game == ScraperHelper.LAGRANDE) {
                    Text(result.date.replace('|', '\n'), modifier = Modifier.fillMaxWidth(), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = accent)
                    Spacer(Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ResultBall(result.result1.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result2.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result3.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result4.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result5.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result6.toString(), borderColor = Color(0xFFFFD700), ballSize = 40.dp, textSize = 14.sp, contentColor = Color(0xFFB8860B))
                    }
                } else {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(result.date.replace('|', '\n'), Modifier.weight(1f), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = accent)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            ResultBall(result.result1.toString().padStart(2, '0'), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                            if (result.result2 >= 0) ResultBall(result.result2.toString(), borderColor = Color(0xFFFF9800), ballSize = 40.dp, textSize = 12.sp, contentColor = Color(0xFFE65100))
                            if (result.month.isNotEmpty()) ResultBall(result.month, borderColor = Color(0xFFFF9800), ballSize = 40.dp, textSize = 12.sp, contentColor = Color(0xFFE65100))
                            if (result.multix.isNotEmpty()) ResultBall(result.multix, borderColor = Color(0xFFFF9800), ballSize = 40.dp, textSize = 12.sp, contentColor = Color(0xFFE65100))
                            if (result.result3 >= 0) ResultBall(result.result3.toString(), borderColor = Color(0xFFFF9800), ballSize = 40.dp, textSize = 12.sp, contentColor = Color(0xFFE65100))
                        }
                    }
                }
            }
        }
    }
}
