package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Componente pequeño para botones reutilizables
@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        // Un fondo un poco más sólido para que no se pierda el contraste
        color = Color.White.copy(alpha = 0.25f),
        shape = CircleShape, // Forma de píldora perfecta
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 20.dp, // Mucho más espacio a los lados
                vertical = 10.dp    // Espacio arriba y abajo para que respire
            ),
            color = Color.White,
            fontSize = 12.sp, // Subimos un punto el tamaño
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp // Un poco de espacio entre letras para legibilidad
        )
    }
}