package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResult
import com.resultados.loto.lotonicaragua.ui.gameAccentColor
import com.resultados.loto.lotonicaragua.ui.gameSurfaceColor
import com.resultados.loto.lotonicaragua.ui.orangeDark
import com.resultados.loto.lotonicaragua.ui.orangeLight
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardDiaria(results: List<DiariaResult>, navController: NavController?) {
    val isDark = isSystemInDarkTheme()
    val accent = gameAccentColor(isDark, ScraperHelper.DIARIA)
    val surface = gameSurfaceColor(isDark, ScraperHelper.DIARIA)

    Card(
        Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = surface)
    ) {
        Column {
            CardTopAccent(accent)
            Column(Modifier.padding(16.dp)) {
                GameBadge({ Icon(Icons.Default.CalendarToday, null, tint = accent, modifier = Modifier.size(18.dp)) }, "Diaria", results.size, accent)
                Spacer(Modifier.height(12.dp))
                results.forEachIndexed { i, item ->
                    SorteoDiaria(item, accent, isDark)
                    if (i < results.size - 1) HorizontalDivider(Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                    AccentButton("ESTAD\u00cdSTICAS", accent) { navController?.navigate(ResultsFragmentDirections.actionNavHomeToNavDiariaStats()) }
                    Spacer(Modifier.width(8.dp))
                    AccentButton("ANTERIORES", accent) { navController?.navigate(ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(ScraperHelper.DIARIA)) }
                }
            }
        }
    }
}

@Composable
fun SorteoDiaria(resultado: DiariaResult, accent: Color, isDark: Boolean) {
    val secondaryAccent = if (isDark) orangeDark else orangeLight
    val secondaryContent = if (isDark) Color(0xFFFFCC80) else Color(0xFFE65100)

    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            val p = resultado.dateString.split('|')
            Text(p.firstOrNull()?.trim() ?: "", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            if (p.size > 1) Text(p[1].trim(), color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            ResultBall(resultado.winningNumber.toString().padStart(2, '0'), borderColor = accent, ballSize = 42.dp, textSize = 17.sp, contentColor = accent)
            ResultBall(resultado.multiX ?: "", borderColor = secondaryAccent, ballSize = 36.dp, textSize = 13.sp, contentColor = secondaryContent)
            resultado.plusOne?.let { ResultBall("$it", borderColor = secondaryAccent, ballSize = 36.dp, textSize = 13.sp, contentColor = secondaryContent) }
        }
    }
}
