package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultBall(
    ballText: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = 17.sp,
    borderColor: Color = Color(0xFF9E9E9E),
    ballSize: Dp = 44.dp,
    contentColor: Color = Color(0xFF424242)
) {
    val enter = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { enter.value = true }
    val scale by animateFloatAsState(
        targetValue = if (enter.value) 1f else 0.3f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 350f)
    )

    Box(
        modifier = modifier
            .size(ballSize)
            .scale(scale)
            .shadow(4.dp, CircleShape, ambientColor = Color.Black.copy(alpha = 0.15f), spotColor = Color.Black.copy(alpha = 0.2f))
            .clip(CircleShape)
            .background(Color.White)
            .border(1.5.dp, borderColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            ballText,
            color = contentColor,
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ResultPill(
    ballText: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = 18.sp,
    borderColor: Color = Color(0xFF9E9E9E),
    pillWidth: Dp = 54.dp,
    pillHeight: Dp = 48.dp,
    contentColor: Color = Color(0xFF424242)
) {
    val enter = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { enter.value = true }
    val scale by animateFloatAsState(
        targetValue = if (enter.value) 1f else 0.3f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 350f)
    )

    Box(
        modifier = modifier
            .size(width = pillWidth, height = pillHeight)
            .scale(scale)
            .shadow(4.dp, RoundedCornerShape(pillHeight / 2), ambientColor = Color.Black.copy(alpha = 0.15f), spotColor = Color.Black.copy(alpha = 0.2f))
            .clip(RoundedCornerShape(pillHeight / 2))
            .background(Color.White)
            .border(1.5.dp, borderColor, RoundedCornerShape(pillHeight / 2)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            ballText,
            color = contentColor,
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
