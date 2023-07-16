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
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = deepOrangeGradient
                )
            ),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color(0xffd84315))
                .padding(16.dp)
        ) {
            Text(text = "Fechas", fontSize = 20.sp, color = Color.White)
            Text(text = "Últimos resultados", color = Color.White,
                fontSize = 12.sp)

        }

        Column (
            Modifier
                .fillMaxWidth()
                .padding(start=16.dp, end = 16.dp, bottom = 4.dp, top = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            results.forEach {item ->
                SorteoJuega(resultado = item)
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ){
            Row(modifier = Modifier.padding(bottom = 12.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    onClick = {
                        val action = ResultsFragmentDirections.actionNavHomeToFechaStatsFragment()
                        navController?.navigate(action)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x44ffab91)),
                    border = BorderStroke(Dp(0f), color = Color.Transparent),
                    shape = RoundedCornerShape(23.dp),
                ) {
                    Text(text = "ESTADISTICAS", color = Color.White, fontSize = 11.sp)
                }
                OutlinedButton(
                    onClick = {
                        val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.FECHAS)
                        navController?.navigate(action)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x44ffab91)),
                    border = BorderStroke(Dp(0f), color = Color.Transparent),
                    shape = RoundedCornerShape(23.dp),
                ) {
                    Text(text = "ANTERIORES", color = Color.White, fontSize = 11.sp)
                }
            }
        }


    }
}

@Composable
fun SorteoJuega(resultado: FechasResult){
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = resultado.dateString.replace('|', '\n'),
            color = Color.White,
            modifier = Modifier.weight(1f),
            fontSize = 13.sp
        )
        ResultBall(
            ballText = resultado.winningNumber.toString(),
            ballColors = grayGradient, ballSize = 40.dp, textSize = 14.sp)
        ResultBall(
            ballText = resultado.winningMonth, ballColors = yellowGradient,
            ballSize = 40.dp, textSize = 12.sp)
    }
}

@Preview
@Composable
fun PreviewFechasCard() {
    CardFechas(listOf(), null)
}