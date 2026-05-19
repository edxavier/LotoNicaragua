package com.resultados.loto.lotonicaragua.ui.home.composes
import com.resultados.loto.lotonicaragua.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResult
import com.resultados.loto.lotonicaragua.ui.grayGradient
import com.resultados.loto.lotonicaragua.ui.greenGradient
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections
import com.resultados.loto.lotonicaragua.ui.lightGreenGradient
import com.resultados.loto.lotonicaragua.ui.orangeGradient
import com.resultados.loto.lotonicaragua.ui.yellowGradient

@Composable
fun CardDiaria(
    results: List<DiariaResult>,
    navController: NavController?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp // Da profundidad a la tarjeta
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(colors = lightGreenGradient))
        ) {
            // Cabecera con un gradiente más sólido
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(colors = greenGradient))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Diaria",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_semibold))
                )
                Text(
                    text = "Últimos resultados",
                    color = Color.White.copy(alpha = 0.8f), // Blanco suavizado
                    fontSize = 13.sp,
                    letterSpacing = 0.5.sp
                )
            }

            // Espacio de resultados
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp), // Espacio entre cada fila de sorteo
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                results.forEach { item ->
                    SorteoDiaria(resultado = item)
                }
            }

            // Divisor sutil opcional
            Divider(color = Color.White.copy(alpha = 0.2f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

            // Fila de acciones (Botones)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de Estadísticas
                ActionButton(text = "ESTADÍSTICAS") {
                    val action = ResultsFragmentDirections.actionNavHomeToNavDiariaStats()
                    navController?.navigate(action)
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón de Anteriores
                ActionButton(text = "ANTERIORES") {
                    val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.DIARIA)
                    navController?.navigate(action)
                }
            }
        }
    }
}


@Composable
fun SorteoDiaria(resultado: DiariaResult) {
    // Usamos una Surface sutil para separar visualmente cada fila si es necesario
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bloque de Fecha/Hora
        Column(
            modifier = Modifier.weight(1f)
        ) {
            val dateParts = resultado.dateString.split('|')
            Text(
                text = dateParts.firstOrNull()?.trim() ?: "",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                letterSpacing = 0.5.sp
            )
            if (dateParts.size > 1) {
                Text(
                    text = dateParts[1].trim(),
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Grupo de Bolas de Resultados
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Número Ganador (Amarillo - Principal)
            ResultBall(
                ballText = resultado.winningNumber.toString().padStart(2, '0'),
                ballColors = grayGradient,
                ballSize = 42.dp, // Un poco más grande por ser el principal
                textSize = 17.sp,
                contentColor = Color.Black // Texto oscuro para contraste en amarillo
            )

            // MultiX (Naranja)
            ResultBall(
                ballText = resultado.multiX,
                ballColors = yellowGradient,
                ballSize = 42.dp,
                textSize = 16.sp,
                contentColor = Color.White
            )

            // PlusOne (Si existe)
            if (resultado.plusOne != null) {
                ResultBall(
                    ballText = "${resultado.plusOne}",
                    ballColors = orangeGradient,
                    ballSize = 42.dp,
                    textSize = 16.sp,
                    contentColor = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    CardDiaria(listOf(), null)
}