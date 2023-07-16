package com.resultados.loto.lotonicaragua.ui.numerologia

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.resultados.loto.lotonicaragua.ui.grayGradient
import com.resultados.loto.lotonicaragua.ui.home.composes.ResultBall
import com.resultados.loto.lotonicaragua.ui.orangeGradient

@Composable
fun Piramide(
    luckyDate: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            luckyDate.forEachIndexed { index, c ->
                val ballColor = if (index % 2 == 0)
                    orangeGradient
                else
                    grayGradient
                ResultBall(
                    ballText = c.toString(),
                    ballSize = 30.dp,
                    ballColors = ballColor
                )
            }
        }
        var reducedString = luckyDate
        var reduced2String = ""
        while (reducedString.length>1){
            var temp = ""
            reducedString.forEachIndexed { index, c ->
                val next = index+1
                if(next<reducedString.length) {
                    var result = "${(c.toString().toInt() + reducedString[next].toString().toInt())}"
                    result = result.last().toString()
                    temp = "$temp$result"
                }
            }
            if(temp.length==2)
                reduced2String = temp
            reducedString = temp
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                reducedString.forEachIndexed { index, c ->
                    val ballColor = if (index % 2 == 0)
                        orangeGradient
                    else
                        grayGradient
                    ResultBall(
                        ballText = c.toString(),
                        ballSize = 30.dp,
                        ballColors = ballColor
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Cruz(last2 = reduced2String, last = reducedString)
    }

}