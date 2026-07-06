package com.resultados.loto.lotonicaragua.ui.numerologia

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultados.loto.lotonicaragua.ui.home.composes.ResultBall
import kotlinx.coroutines.delay

@Composable
fun Piramide(luckyDate: String) {
    val levels = remember(luckyDate) { mutableStateListOf<String>() }
    var reduced2String by remember { mutableStateOf("") }
    val visibleLevels = remember { mutableStateOf(0) }

    LaunchedEffect(luckyDate) {
        if (levels.isEmpty()) {
            levels.add(luckyDate)
            var reduced = luckyDate
            while (reduced.length > 1) {
                var temp = ""
                reduced.forEachIndexed { index, c ->
                    val next = index + 1
                    if (next < reduced.length) {
                        val sum = c.toString().toInt() + reduced[next].toString().toInt()
                        temp = "$temp${sum.toString().last()}"
                    }
                }
                if (temp.length == 2) reduced2String = temp
                reduced = temp
                levels.add(temp)
            }
            for (i in 0 until levels.size) {
                visibleLevels.value = i + 1
                delay(180)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(listOf(Color(0xFFF3E5F5), Color(0xFFEDE7F6).copy(alpha = 0.6f)))
            )
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("PIRÁMIDE NUMEROLÓGICA", color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold, fontSize = 13.sp, letterSpacing = 1.5.sp)
        Spacer(Modifier.height(16.dp))

        levels.forEachIndexed { levelIndex, levelStr ->
            val show by animateFloatAsState(
                targetValue = if (levelIndex < visibleLevels.value) 1f else 0f,
                animationSpec = spring(dampingRatio = 0.55f, stiffness = 300f)
            )
            if (show > 0f) {
                Column(
                    Modifier.graphicsLayer { alpha = show },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (levelIndex > 0) {
                        Row(
                            Modifier.height(14.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val prevLen = levels[levelIndex - 1].length
                            repeat(prevLen - 1) {
                                Box(
                                    Modifier
                                        .padding(horizontal = 12.dp)
                                        .size(3.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF7B1FA2).copy(alpha = 0.3f))
                                )
                            }
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        levelStr.forEachIndexed { index, c ->
                            val isLastRow = levelIndex == levels.size - 1
                            val borderColor = if (isLastRow)
                                Color(0xFFFFD700)
                            else if (index % 2 == 0) Color(0xFFFF9800)
                            else Color(0xFF9E9E9E)
                            val textColor = if (isLastRow) Color(0xFFB8860B) else if (index % 2 == 0) Color(0xFFE65100) else Color(0xFF424242)
                            val size = if (isLastRow) 38.dp else 32.dp
                            ResultBall(c.toString(), borderColor = borderColor, ballSize = size, textSize = if (isLastRow) 16.sp else 12.sp, contentColor = textColor)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        val last = levels.lastOrNull() ?: ""
        Cruz(last2 = reduced2String, last = last)
    }
}
