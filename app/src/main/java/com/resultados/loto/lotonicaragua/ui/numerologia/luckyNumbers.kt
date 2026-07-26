package com.resultados.loto.lotonicaragua.ui.numerologia

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LuckyNumbers() {
    val isDark = isSystemInDarkTheme()
    val sdf = SimpleDateFormat("dd 'de' MMMM yyyy", Locale("es", "NI"))
    val luckyDateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
    val currentDate = sdf.format(Date())
    val luckyDate = luckyDateFormat.format(Date())

    val headerAlpha by animateFloatAsState(1f, tween(500))

    val bgColor = if (isDark) Color(0xFF1A0E28) else Color(0xFFFAF5FF)
    val bgEnd = if (isDark) Color(0xFF12061E) else Color(0xFFF3E5F5)
    val headerStart = if (isDark) Color(0xFF3D0E5E) else Color(0xFF5E1A8A)
    val headerEnd = if (isDark) Color(0xFF1A042E) else Color(0xFF3D0E5E)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(bgColor, bgEnd), startY = 0f, endY = 400f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .graphicsLayer { alpha = headerAlpha }
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.horizontalGradient(listOf(headerStart, headerEnd)))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.AutoAwesome, null, tint = Color(0xFFFFD54F), modifier = Modifier.size(26.dp))
                    Text("N\u00fameros de la Suerte", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                }
                Spacer(Modifier.height(8.dp))
                Text(currentDate, color = Color.White.copy(alpha = 0.8f), fontSize = 15.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Medium)
            }
        }
        item { Spacer(Modifier.height(16.dp)) }
        item { Piramide(luckyDate = luckyDate) }
        item { Spacer(Modifier.height(40.dp)) }
    }
}
