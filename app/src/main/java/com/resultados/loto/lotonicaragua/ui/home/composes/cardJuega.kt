package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.api.models.base.BaseResult
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import com.resultados.loto.lotonicaragua.ui.*
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardJuega(
    results: List<BaseResult>,
    navController: NavController?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(colors = cyanGradient)) // Asegúrate que sea un cian suave
        ) {
            // Cabecera: Cian Oscuro / Teal
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF006064)) // Cian muy profundo para elegancia
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Juega3",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Últimos resultados",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 13.sp
                )
            }

            // Lista de Resultados
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                results.forEach { item ->
                    SorteoJuega(resultado = item)
                }
            }

            // Botón ANTERIORES mejorado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                ActionButton(text = "ANTERIORES") {
                    val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.JUEGA3)
                    navController?.navigate(action)
                }
            }
        }
    }
}


@Composable
fun SorteoJuega(resultado: BaseResult) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bloque de Fecha
        Column(modifier = Modifier.weight(1f)) {
            val parts = resultado.dateString.split('|')
            Text(
                text = parts.firstOrNull()?.trim() ?: "",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            if (parts.size > 1) {
                Text(
                    text = parts[1].trim(),
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            }
        }

        // Bola de Resultado - Juega3
        ResultBall(
            ballText = resultado.winningNumber.toString().padStart(3, '0'),
            // Gradiente Amarillo/Oro
            ballColors = listOf(Color(0xFFFFD54F), Color(0xFFF57F17)),
            ballSize = 46.dp, // Un poco más grande para que quepan bien los 3 números
            textSize = 18.sp,
            contentColor = Color(0xFFF0F0F0) // Texto oscuro para máximo contraste
        )
    }
}

@Preview
@Composable
fun PreviewSorteoCard() {
    CardJuega(listOf(), null)
}