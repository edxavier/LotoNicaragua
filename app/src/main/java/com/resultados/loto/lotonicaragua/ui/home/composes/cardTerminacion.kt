package com.resultados.loto.lotonicaragua.ui.home.composes

import com.resultados.loto.lotonicaragua.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
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
import com.resultados.loto.lotonicaragua.data.api.models.base.BaseResult
import com.resultados.loto.lotonicaragua.ui.*
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardTerminacion(results: List<BaseResult>, navController: NavController?) {
    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), colors = CardDefaults.cardColors(containerColor = pastelTerminacion)) {
        Column {
            CardTopAccent(accentTerminacion)
            Column(Modifier.padding(16.dp)) {
                GameBadge({ Icon(Icons.Default.Tag, null, tint = accentTerminacion, modifier = Modifier.size(18.dp)) }, "Terminación 2", results.size, accentTerminacion)
                Spacer(Modifier.height(12.dp))
                results.forEachIndexed { i, item ->
                    SorteoTerminacion(item)
                    if (i < results.size - 1) HorizontalDivider(Modifier.padding(vertical = 2.dp), color = Color(0xFFEEEEEE))
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    AccentButton("ANTERIORES", accentTerminacion) { navController?.navigate(ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(ScraperHelper.TERMINACION2)) }
                }
            }
        }
    }
}

@Composable
fun SorteoTerminacion(resultado: BaseResult) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            val p = resultado.dateString.split('|')
            Text(p.firstOrNull()?.trim() ?: "", color = Color(0xFF212121), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            if (p.size > 1) Text(p[1].trim(), color = Color(0xFF9E9E9E), fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
        ResultBall(resultado.winningNumber.toString(), borderColor = accentTerminacion, ballSize = 42.dp, textSize = 16.sp, contentColor = accentTerminacion)
    }
}
