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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.api.models.base.BaseResult
import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResult
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import com.resultados.loto.lotonicaragua.data.api.models.grande.GrandeResult
import com.resultados.loto.lotonicaragua.ui.*
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardGrande(
    results: List<GrandeResult>,
    navController: NavController?
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = goldGradient
                )
            ),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color(0xff5d4037))
                .padding(16.dp)
        ) {
            Text(text = "La grande", fontSize = 20.sp, color = Color.White)
            Text(text = "Últimos resultados", color = Color.White,
                fontSize = 12.sp)
        }

        Column (
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            results.forEach {item ->
                SorteoGrande(resultado = item)
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
                        val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.LAGRANDE)
                        navController?.navigate(action)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x44ffe082)),
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
fun SorteoGrande(resultado: GrandeResult){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =  Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = resultado.dateString.replace('|', '\n'),
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 13.sp
        )
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textSize = 14.sp
            ResultBall(
                ballText = resultado.number1.toString(),
                ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
            ResultBall(
                ballText = resultado.number2.toString(),
                ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
            ResultBall(
                ballText = resultado.number3.toString(),
                ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
            ResultBall(
                ballText = resultado.number4.toString(),
                ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
            ResultBall(
                ballText = resultado.number5.toString(),
                ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
            ResultBall(
                ballText = resultado.gold.toString(),
                ballColors = yellowGradient, ballSize = 40.dp, textSize = textSize)

        }
    }

}

@Preview
@Composable
fun PreviewGrandeCard() {
    CardGrande(listOf(), null)
}