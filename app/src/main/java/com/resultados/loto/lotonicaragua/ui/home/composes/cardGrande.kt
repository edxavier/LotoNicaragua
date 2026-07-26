package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.api.models.grande.GrandeResult
import com.resultados.loto.lotonicaragua.ui.gameAccentColor
import com.resultados.loto.lotonicaragua.ui.gameSurfaceColor
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardGrande(results: List<GrandeResult>, navController: NavController?) {
    val isDark = isSystemInDarkTheme()
    val accent = gameAccentColor(isDark, ScraperHelper.LAGRANDE)
    val surface = gameSurfaceColor(isDark, ScraperHelper.LAGRANDE)

    Card(
        Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = surface)
    ) {
        Column {
            CardTopAccent(accent)
            Column(Modifier.padding(16.dp)) {
                GameBadge({ Icon(Icons.Default.Diamond, null, tint = accent, modifier = Modifier.size(18.dp)) }, "La Grande", results.size, accent)
                Spacer(Modifier.height(12.dp))
                results.forEachIndexed { i, item ->
                    SorteoGrande(item, accent, isDark)
                    if (i < results.size - 1) HorizontalDivider(Modifier.padding(vertical = 6.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    AccentButton("ANTERIORES", accent) { navController?.navigate(ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(ScraperHelper.LAGRANDE)) }
                }
            }
        }
    }
}

@Composable
fun SorteoGrande(resultado: GrandeResult, accent: Color, isDark: Boolean) {
    val goldAccent = Color(0xFFFFD700)
    val goldContent = if (isDark) Color(0xFFFFE082) else Color(0xFFB8860B)

    val pulse = rememberInfiniteTransition(label = "goldPulse")
    val goldScale by pulse.animateFloat(
        initialValue = 1f, targetValue = 1.06f,
        animationSpec = infiniteRepeatable(tween(1600, easing = LinearEasing), RepeatMode.Reverse),
        label = "goldPulse"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            resultado.dateString.replace('|', '\n'),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                ResultBall(resultado.number1.toString(), borderColor = accent, ballSize = 38.dp, textSize = 15.sp, contentColor = accent)
                ResultBall(resultado.number2.toString(), borderColor = accent, ballSize = 38.dp, textSize = 15.sp, contentColor = accent)
                ResultBall(resultado.number3.toString(), borderColor = accent, ballSize = 38.dp, textSize = 15.sp, contentColor = accent)
                ResultBall(resultado.number4.toString(), borderColor = accent, ballSize = 38.dp, textSize = 15.sp, contentColor = accent)
                ResultBall(resultado.number5.toString(), borderColor = accent, ballSize = 38.dp, textSize = 15.sp, contentColor = accent)
            }
        }
        Box(contentAlignment = Alignment.Center) {
                Box(
                    Modifier
                        .size(56.dp)
                        .scale(goldScale)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(goldAccent.copy(alpha = 0.25f), Color.Transparent)
                            )
                        )
                )
            Box(
                Modifier
                    .size(46.dp)
                    .shadow(if (isDark) 8.dp else 4.dp, CircleShape, ambientColor = goldAccent.copy(alpha = 0.25f), spotColor = goldAccent.copy(alpha = 0.35f))
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .scale(goldScale),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    resultado.gold.toString(),
                    color = goldContent,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
