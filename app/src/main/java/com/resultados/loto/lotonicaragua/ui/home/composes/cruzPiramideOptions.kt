package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.resultados.loto.lotonicaragua.ui.theme.LocalResultsViewModel

@Composable
fun CruzPiramideOptions(onClick: () -> Unit) {
    val viewModel = LocalResultsViewModel.current
    val infinite = rememberInfiniteTransition(label = "pulse")
    val pulse by infinite.animateFloat(
        initialValue = 1f, targetValue = 1.03f,
        animationSpec = infiniteRepeatable(tween(1200), RepeatMode.Reverse),
        label = "pulse"
    )

    val containerColor = MaterialTheme.colorScheme.primaryContainer
    val contentColor = MaterialTheme.colorScheme.onPrimaryContainer

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Surface(
            onClick = {
                viewModel?.triggerInterstitial()
                onClick()
            },
            shape = RoundedCornerShape(24.dp),
            color = containerColor,
            tonalElevation = 4.dp,
            shadowElevation = 6.dp
        ) {
            Row(
                modifier = Modifier
                    .scale(pulse)
                    .padding(horizontal = 18.dp, vertical = 11.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.AutoAwesome, null, tint = contentColor, modifier = Modifier.size(18.dp))
                Text(
                    "PIR\u00c1MIDE + CRUZ de la Suerte",
                    color = contentColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
