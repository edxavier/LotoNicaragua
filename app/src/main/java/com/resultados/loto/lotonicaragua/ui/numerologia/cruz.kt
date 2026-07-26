package com.resultados.loto.lotonicaragua.ui.numerologia

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultados.loto.lotonicaragua.ui.home.composes.ResultBall

@Composable
fun Cruz(last2: String, last: String) {
    if (last.isEmpty()) return
    val isDark = isSystemInDarkTheme()

    val last2Safe = if (last2.length >= 2) last2 else last2.padEnd(2, '0')
    val top = last
    val left = (top.toInt() + last2Safe[0].toString().toInt()).toString().last().toString()
    val right = (top.toInt() + last2Safe[1].toString().toInt()).toString().last().toString()
    val bottom = (left.toInt() + right.toInt()).toString().last().toString()
    val topLeft = (top.toInt() + left.toInt()).toString().last().toString()
    val topRight = (top.toInt() + right.toInt()).toString().last().toString()
    val bottomLeft = (bottom.toInt() + left.toInt()).toString().last().toString()
    val bottomRight = (bottom.toInt() + right.toInt()).toString().last().toString()

    val ballSize = 36.dp
    val cellSize = 56.dp
    val gridSize = cellSize * 5

    val visible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible.value = true }
    val crossFade by animateFloatAsState(if (visible.value) 1f else 0f, tween(600))

    val titleColor = if (isDark) Color(0xFFCE93D8) else Color(0xFF7B1FA2)
    val cardBg = if (isDark) Color(0xFF211838).copy(alpha = 0.5f) else Color(0xFFF5F0FF).copy(alpha = 0.5f)
    val glowColor = if (isDark) Color(0xFFAB47BC).copy(alpha = 0.12f) else Color(0xFFCE93D8).copy(alpha = 0.12f)
    val crossColor = if (isDark) Color(0xFFAB47BC).copy(alpha = 0.4f) else Color(0xFF7B1FA2).copy(alpha = 0.4f)
    val borderColor = if (isDark) Color(0xFFCE93D8) else Color(0xFF7B1FA2)
    val labelColor = if (isDark) Color(0xFFCE93D8) else Color(0xFF6A1B9A)
    val numberColor = if (isDark) Color(0xFFCE93D8) else Color(0xFF7B1FA2)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("CRUZ DE LA SUERTE", color = titleColor, fontWeight = FontWeight.Bold, fontSize = 13.sp, letterSpacing = 1.5.sp)
        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(cardBg)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(gridSize + 20.dp)
                    .clip(CircleShape)
                    .background(Brush.radialGradient(listOf(glowColor, Color.Transparent)))
            )

            Column(
                Modifier.size(gridSize),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    ResultBall(topLeft, ballSize = ballSize, textSize = 13.sp, borderColor = borderColor); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                    ResultBall(topRight, ballSize = ballSize, textSize = 13.sp, borderColor = borderColor)
                }
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                    ResultBall(top, ballSize = ballSize + 4.dp, textSize = 16.sp, borderColor = Color(0xFFFFD700), contentColor = Color(0xFFB8860B))
                    Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                }
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(ballSize))
                    ResultBall(left, ballSize = ballSize, textSize = 13.sp, borderColor = borderColor); Box(Modifier.size(ballSize))
                    ResultBall(right, ballSize = ballSize, textSize = 13.sp, borderColor = borderColor); Box(Modifier.size(ballSize))
                }
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                    ResultBall(bottom, ballSize = ballSize, textSize = 13.sp, borderColor = borderColor); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                }
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    ResultBall(bottomLeft, ballSize = ballSize, textSize = 13.sp, borderColor = borderColor); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                    ResultBall(bottomRight, ballSize = ballSize, textSize = 13.sp, borderColor = borderColor)
                }
            }

            Canvas(
                Modifier.size(gridSize * 0.76f).graphicsLayer { alpha = crossFade }
            ) {
                val p = size.width * 0.12f
                drawLine(crossColor, Offset(p, p), Offset(size.width - p, size.height - p), 2.dp.toPx())
                drawLine(crossColor, Offset(size.width - p, p), Offset(p, size.height - p), 2.dp.toPx())
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("N\u00daMERO DE SUERTE:", color = labelColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
            Text(top, color = numberColor, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}
