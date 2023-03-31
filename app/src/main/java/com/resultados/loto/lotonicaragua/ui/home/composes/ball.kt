package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultados.loto.lotonicaragua.ui.grayGradient


@Composable
fun ResultBall(
    ballText: String, textSize: TextUnit = 20.sp,
    ballColors: List<Color> = grayGradient,
    ballSize: Dp = 50.dp
){
    Box(
        modifier = Modifier
            .size(ballSize)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = ballColors,
                    radius = 50f,
                    center = Offset(20f, 25f),
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = ballText, fontSize = textSize, fontWeight = FontWeight(500))
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    ResultBall("ABRIL", textSize = 15.sp)
}
