package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
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
import com.resultados.loto.lotonicaragua.data.api.models.base.BaseResult
import com.resultados.loto.lotonicaragua.ui.gameAccentColor
import com.resultados.loto.lotonicaragua.ui.gameSurfaceColor
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardTerminacion(results: List<BaseResult>, navController: NavController?) {
    val isDark = isSystemInDarkTheme()
    val accent = gameAccentColor(isDark, ScraperHelper.TERMINACION2)
    val surface = gameSurfaceColor(isDark, ScraperHelper.TERMINACION2)

    Card(
        Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = surface)
    ) {
        Column {
            CardTopAccent(accent)
            Column(Modifier.padding(16.dp)) {
                GameBadge({ Icon(Icons.Default.Tag, null, tint = accent, modifier = Modifier.size(18.dp)) }, "Terminaci\u00f3n 2", results.size, accent)
                Spacer(Modifier.height(12.dp))
                results.forEachIndexed { i, item ->
                    SorteoTerminacion(item, accent)
                    if (i < results.size - 1) HorizontalDivider(Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    AccentButton("ANTERIORES", accent) { navController?.navigate(ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(ScraperHelper.TERMINACION2)) }
                }
            }
        }
    }
}

@Composable
fun SorteoTerminacion(resultado: BaseResult, accent: Color) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            val p = resultado.dateString.split('|')
            Text(p.firstOrNull()?.trim() ?: "", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            if (p.size > 1) Text(p[1].trim(), color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
        ResultBall(resultado.winningNumber.toString(), borderColor = accent, ballSize = 42.dp, textSize = 16.sp, contentColor = accent)
    }
}
