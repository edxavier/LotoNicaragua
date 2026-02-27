package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultados.loto.lotonicaragua.ui.grayGradient


@Composable
fun ResultBall(
    ballText: String,
    modifier: Modifier = Modifier,
    textSize: TextUnit = 14.sp,
    // Usamos colores con suficiente peso visual
    ballColors: List<Color> = listOf(Color(0xFF6D94C5), Color(0xFF0F2854)),
    ballSize: Dp = 44.dp,
    contentColor: Color = Color.White // Por defecto blanco para gradientes oscuros
) {
    Box(
        modifier = modifier
            .size(ballSize)
            .shadow(elevation = 6.dp, shape = CircleShape)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = ballColors,
                    center = Offset(0.3f, 0.3f),
                    radius = Float.POSITIVE_INFINITY
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Brillo superior para volumen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.White.copy(alpha = 0.15f), Color.Transparent),
                        endY = 40f
                    )
                )
        )

        Text(
            text = ballText,
            color = contentColor,
            fontSize = textSize,
            fontWeight = FontWeight.ExtraBold, // Más peso ayuda al contraste
            textAlign = TextAlign.Center,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f),
                    offset = Offset(0f, 2f),
                    blurRadius = 4f
                )
            )
        )
    }
}


@Preview
@Composable
fun PreviewMessageCard() {
    ResultBall("ABRIL", textSize = 11.sp)
}
