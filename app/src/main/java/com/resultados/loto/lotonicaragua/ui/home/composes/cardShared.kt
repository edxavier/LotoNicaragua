package com.resultados.loto.lotonicaragua.ui.home.composes

import com.resultados.loto.lotonicaragua.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardTopAccent(accent: Color) = Box(Modifier.fillMaxWidth().height(3.dp).background(accent))

@Composable
fun GameBadge(icon: @Composable () -> Unit, name: String, count: Int, accent: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Surface(shape = RoundedCornerShape(10.dp), color = accent.copy(alpha = 0.12f)) {
            Row(Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                icon()
                Text(name, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.source_sans_pro_semibold)), color = accent)
            }
        }
        Text("Últimos $count sorteo${if (count != 1) "s" else ""}", fontSize = 11.sp, color = Color(0xFF9E9E9E), letterSpacing = 0.3.sp)
    }
}

@Composable
fun AccentButton(text: String, accent: Color, onClick: () -> Unit) {
    Surface(onClick = onClick, shape = RoundedCornerShape(16.dp), color = accent.copy(alpha = 0.1f)) {
        Text(text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), color = accent, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
    }
}
