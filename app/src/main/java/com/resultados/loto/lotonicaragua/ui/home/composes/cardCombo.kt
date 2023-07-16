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
import com.resultados.loto.lotonicaragua.data.api.models.base.BaseResult
import com.resultados.loto.lotonicaragua.data.api.models.combo.ComboResult
import com.resultados.loto.lotonicaragua.data.api.models.fechas.FechasResult
import com.resultados.loto.lotonicaragua.ui.*
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CardCombo(
    results: List<ComboResult>,
    navController: NavController?
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = purpleGradient
                )
            ),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color(0xff6a1b9a))
                .padding(16.dp)
        ) {
            Text(text = "Super Combo", fontSize = 20.sp, color = Color.White)
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
                SorteoCombo(resultado = item)
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
                        val action = ResultsFragmentDirections.actionNavHomeToPreviousResultsFragment(sorteo = ScraperHelper.SUPERCOMBO)
                        navController?.navigate(action)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x44ce93d8)),
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
fun SorteoCombo(resultado: ComboResult){
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = resultado.dateString.replace('|', '\n'),
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        ResultBall(
            ballText = resultado.winningNumber1.toString().padStart(2,'0'),
            ballColors = grayGradient, ballSize = 40.dp, textSize = 14.sp)
        ResultBall(
            ballText = resultado.winningNumber2.toString().padStart(2,'0'),
            ballColors = yellowGradient, ballSize = 40.dp, textSize = 14.sp)
    }
}

@Preview
@Composable
fun PreviewComboCard() {
    CardJuega(listOf(), null)
}