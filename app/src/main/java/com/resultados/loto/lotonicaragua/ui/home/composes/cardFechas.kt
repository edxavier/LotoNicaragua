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
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import com.resultados.loto.lotonicaragua.ui.*
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections


@Composable
fun CardFechas(
    results: List<FechasResult>,
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
                .background(Brush.verticalGradient(colors = deepOrangeGradient))
        ) {
            // Cabecera Sólida
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFBF360C)) // Un naranja un poco más profundo para contraste
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Fechas",
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

            // Fila de Botones con el nuevo estilo ActionButton
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                ActionButton(text = "ESTADÍSTICAS") {
                    val action = ResultsFragmentDirections.actionNavHomeToFechaStatsFragment()
                    navController?.navigate(action)
                }

                Spacer(modifier = Modifier.width(12.dp))

                ActionButton(text = "ANTERIORES") {
                    val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.FECHAS)
                    navController?.navigate(action)
                }
            }
        }
    }
}

@Composable
fun SorteoJuega(resultado: FechasResult) {
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

        // Bolas de Resultado
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Número del día (Gris Intenso)
            ResultBall(
                ballText = resultado.winningNumber.toString().padStart(2, '0'),
                // ballColors = listOf(Color(0xFF547792), Color(0xFF212121)),
                ballSize = 42.dp,
                textSize = 14.sp,
                contentColor = Color.White
            )

            // Mes (Amarillo Brillante)
            ResultBall(
                ballText = resultado.winningMonth.uppercase(),
                ballColors = yellowGradient,
                ballSize = 42.dp,
                textSize = 11.sp, // Texto más pequeño porque los meses son largos
                contentColor = Color(0xFF333333) // TEXTO OSCURO para leer "ABRIL", "OCTUBRE", etc.
            )
        }
    }
}

@Preview
@Composable
fun PreviewFechasCard() {
    CardFechas(listOf(), null)
}