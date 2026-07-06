package com.resultados.loto.lotonicaragua.ui.home.composes

import com.resultados.loto.lotonicaragua.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import com.resultados.loto.lotonicaragua.ui.*
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardFechas(results: List<FechasResult>, navController: NavController?) {
    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), colors = CardDefaults.cardColors(containerColor = pastelFechas)) {
        Column {
            CardTopAccent(accentFechas)
            Column(Modifier.padding(16.dp)) {
                GameBadge({ Icon(Icons.Default.DateRange, null, tint = accentFechas, modifier = Modifier.size(18.dp)) }, "Fechas", results.size, accentFechas)
                Spacer(Modifier.height(12.dp))
                results.forEachIndexed { i, item ->
                    SorteoFechas(item)
                    if (i < results.size - 1) HorizontalDivider(Modifier.padding(vertical = 2.dp), color = Color(0xFFEEEEEE))
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                    AccentButton("ESTADÍSTICAS", accentFechas) { navController?.navigate(ResultsFragmentDirections.actionNavHomeToFechaStatsFragment()) }
                    Spacer(Modifier.width(8.dp))
                    AccentButton("ANTERIORES", accentFechas) { navController?.navigate(ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(ScraperHelper.FECHAS)) }
                }
            }
        }
    }
}

@Composable
fun SorteoFechas(resultado: FechasResult) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            val p = resultado.dateString.split('|')
            Text(p.firstOrNull()?.trim() ?: "", color = Color(0xFF212121), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            if (p.size > 1) Text(p[1].trim(), color = Color(0xFF9E9E9E), fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            ResultBall(resultado.winningNumber.toString().padStart(2, '0'), borderColor = accentFechas, ballSize = 42.dp, textSize = 17.sp, contentColor = accentFechas)
            ResultBall((resultado.winningMonth ?: "").uppercase(), borderColor = Color(0xFFFF9800), ballSize = 36.dp, textSize = 10.sp, contentColor = Color(0xFFE65100))
        }
    }
}
