package com.resultados.loto.lotonicaragua.ui.numerologia

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("CRUZ DE LA SUERTE", color = Color(0xFF7B1FA2), fontWeight = FontWeight.Bold, fontSize = 13.sp, letterSpacing = 1.5.sp)
        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFF5F0FF).copy(alpha = 0.5f))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(gridSize + 20.dp)
                    .clip(CircleShape)
                    .background(Brush.radialGradient(listOf(Color(0xFFCE93D8).copy(alpha = 0.12f), Color.Transparent)))
            )

            Column(
                Modifier.size(gridSize),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    ResultBall(topLeft, ballSize = ballSize, textSize = 13.sp, borderColor = Color(0xFF7B1FA2)); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                    ResultBall(topRight, ballSize = ballSize, textSize = 13.sp, borderColor = Color(0xFF7B1FA2))
                }
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                    ResultBall(top, ballSize = ballSize + 4.dp, textSize = 16.sp, borderColor = Color(0xFFFFD700), contentColor = Color(0xFFB8860B))
                    Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                }
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(ballSize))
                    ResultBall(left, ballSize = ballSize, textSize = 13.sp, borderColor = Color(0xFF7B1FA2)); Box(Modifier.size(ballSize))
                    ResultBall(right, ballSize = ballSize, textSize = 13.sp, borderColor = Color(0xFF7B1FA2)); Box(Modifier.size(ballSize))
                }
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                    ResultBall(bottom, ballSize = ballSize, textSize = 13.sp, borderColor = Color(0xFF7B1FA2)); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                }
                Row(Modifier.width(gridSize).height(cellSize), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    ResultBall(bottomLeft, ballSize = ballSize, textSize = 13.sp, borderColor = Color(0xFF7B1FA2)); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize)); Box(Modifier.size(ballSize))
                    ResultBall(bottomRight, ballSize = ballSize, textSize = 13.sp, borderColor = Color(0xFF7B1FA2))
                }
            }

            Canvas(
                Modifier.size(gridSize * 0.76f).graphicsLayer { alpha = crossFade }
            ) {
                val p = size.width * 0.12f
                drawLine(Color(0xFF7B1FA2).copy(alpha = 0.4f), Offset(p, p), Offset(size.width - p, size.height - p), 2.dp.toPx())
                drawLine(Color(0xFF7B1FA2).copy(alpha = 0.4f), Offset(size.width - p, p), Offset(p, size.height - p), 2.dp.toPx())
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("NÚMERO DE SUERTE:", color = Color(0xFF6A1B9A), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
            Text(top, color = Color(0xFF7B1FA2), fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}
