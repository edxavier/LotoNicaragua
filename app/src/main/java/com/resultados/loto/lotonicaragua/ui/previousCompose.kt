package com.resultados.loto.lotonicaragua.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.LotoResult
import com.resultados.loto.lotonicaragua.data.api.models.grande.GrandeResult
import com.resultados.loto.lotonicaragua.ui.home.composes.ResultBall

@Composable
fun PreviousResults(results: List<LotoResult>){
    LazyColumn(
        modifier = Modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(results){item ->
            PreviousResult(item)
        }
    }
}

@Composable
fun PreviousResult(result: LotoResult){
    if (result.game == ScraperHelper.LAGRANDE){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =  Modifier.fillMaxWidth()
                .clip(shape = RoundedCornerShape(12.dp))
                .background(Color(0xfff5f5f5))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = result.date.replace('|', '\n'),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val textSize = 14.sp
                ResultBall(
                    ballText = result.result1.toString(),
                    ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
                ResultBall(
                    ballText = result.result2.toString(),
                    ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
                ResultBall(
                    ballText = result.result3.toString(),
                    ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
                ResultBall(
                    ballText = result.result4.toString(),
                    ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
                ResultBall(
                    ballText = result.result5.toString(),
                    ballColors = grayGradient, ballSize = 40.dp, textSize = textSize)
                ResultBall(
                    ballText = result.result6.toString(),
                    ballColors = yellowGradient, ballSize = 40.dp, textSize = textSize)

            }
        }
    }else{
        Row(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color(0xfff5f5f5))
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val firstBallColor = if (result.game == ScraperHelper.SUPERCOMBO || result.game == ScraperHelper.FECHAS) grayGradient else yellowGradient

            Text(text = result.date.replace('|', '\n'),
                modifier = Modifier.weight(1f), fontSize = 12.sp
            )
            ResultBall(
                ballText = result.result1.toString().padStart(2,'0'),
                ballColors = firstBallColor, ballSize = 40.dp, textSize = 14.sp)
            if (result.result2 >= 0)
                ResultBall(ballText = result.result2.toString(), ballColors = yellowGradient, ballSize = 40.dp, textSize = 12.sp)
            if (result.month.isNotEmpty())
                ResultBall(ballText = result.month, ballColors = yellowGradient, ballSize = 40.dp, textSize = 12.sp)
            if (result.multix.isNotEmpty())
                ResultBall(ballText = result.multix, ballColors = orangeGradient, ballSize = 40.dp, textSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPreviousResults() {
    PreviousResult(LotoResult())
}
