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
import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResult
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import com.resultados.loto.lotonicaragua.ui.*
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardCombo(
    results: List<ComboResult>,
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
                .background(Brush.verticalGradient(colors = purpleGradient))
        ) {
            // Cabecera: Morado Profundo
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4A148C)) // Morado más oscuro para jerarquía
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Super Combo",
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
                    SorteoCombo(resultado = item)
                }
            }

            // Fila de Acción
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                ActionButton(text = "ANTERIORES") {
                    val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.SUPERCOMBO)
                    navController?.navigate(action)
                }
            }
        }
    }
}


@Composable
fun SorteoCombo(resultado: ComboResult) {
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
                fontSize = 15.sp
            )
            if (parts.size > 1) {
                Text(
                    text = parts[1].trim(),
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 13.sp
                )
            }
        }

        // Par de Bolas
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Primer número (Azul Acero / Gris Profundo)
            ResultBall(
                ballText = resultado.winningNumber1.toString().padStart(2, '0'),
                ballColors = grayGradient,
                ballSize = 42.dp,
                textSize = 16.sp,
                contentColor = Color.Black
            )

            // Segundo número (Oro / Amarillo)
            ResultBall(
                ballText = resultado.winningNumber2.toString().padStart(2, '0'),
                ballColors = orangeGradient,
                ballSize = 42.dp,
                textSize = 16.sp,
                contentColor = Color(0xFFF0F0F0) // Texto oscuro para contraste
            )
        }
    }
}

@Preview
@Composable
fun PreviewComboCard() {
    CardJuega(listOf(), null)
}