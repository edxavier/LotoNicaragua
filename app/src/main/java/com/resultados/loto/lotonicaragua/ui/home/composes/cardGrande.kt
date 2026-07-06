package com.resultados.loto.lotonicaragua.ui.home.composes

import com.resultados.loto.lotonicaragua.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.api.models.grande.GrandeResult
import com.resultados.loto.lotonicaragua.ui.*
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardGrande(results: List<GrandeResult>, navController: NavController?) {
    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), colors = CardDefaults.cardColors(containerColor = pastelGrande)) {
        Column {
            CardTopAccent(accentGrande)
            Column(Modifier.padding(16.dp)) {
                GameBadge({ Icon(Icons.Default.Diamond, null, tint = accentGrande, modifier = Modifier.size(18.dp)) }, "La Grande", results.size, accentGrande)
                Spacer(Modifier.height(12.dp))
                results.forEachIndexed { i, item ->
                    SorteoGrande(item)
                    if (i < results.size - 1) HorizontalDivider(Modifier.padding(vertical = 4.dp), color = Color(0xFFEEEEEE))
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    AccentButton("ANTERIORES", accentGrande) { navController?.navigate(ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(ScraperHelper.LAGRANDE)) }
                }
            }
        }
    }
}

@Composable
fun SorteoGrande(resultado: GrandeResult) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(resultado.dateString.replace('|', '\n'), color = Color(0xFF212121), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                ResultBall(resultado.number1.toString(), borderColor = accentGrande, ballSize = 38.dp, textSize = 15.sp, contentColor = accentGrande)
                ResultBall(resultado.number2.toString(), borderColor = accentGrande, ballSize = 38.dp, textSize = 15.sp, contentColor = accentGrande)
                ResultBall(resultado.number3.toString(), borderColor = accentGrande, ballSize = 38.dp, textSize = 15.sp, contentColor = accentGrande)
                ResultBall(resultado.number4.toString(), borderColor = accentGrande, ballSize = 38.dp, textSize = 15.sp, contentColor = accentGrande)
                ResultBall(resultado.number5.toString(), borderColor = accentGrande, ballSize = 38.dp, textSize = 15.sp, contentColor = accentGrande)
            }
        }
        ResultBall(resultado.gold.toString(), borderColor = Color(0xFFFFD700), ballSize = 42.dp, textSize = 17.sp, contentColor = Color(0xFFB8860B))
    }
}
