package com.resultados.loto.lotonicaragua.ui.numerologia

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.resultados.loto.lotonicaragua.ui.home.composes.ResultBall

@Composable
fun Cruz(
    last2: String,
    last: String
) {
    val top = last
    val left = (top.toInt() + last2.first().toString().toInt()).toString().last().toString()
    val right = (top.toInt() + last2.last().toString().toInt()).toString().last().toString()
    val bottom = (left.toInt() + right.toInt()).toString().last().toString()
    val topLeft = (top.toInt() + left.toInt()).toString().last().toString()
    val topRight = (top.toInt() + right.toInt()).toString().last().toString()
    val bottomLeft = (bottom.toInt() + left.toInt()).toString().last().toString()
    val bottomRight = (bottom.toInt() + right.toInt()).toString().last().toString()

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        val cHeight = 45.dp
        val cWidth = 60.dp
        val canvasHeight = cHeight*5
        val canvasWidth = cWidth*5
        Column(
        ){
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .width(canvasWidth)
                    .height(cHeight)
            ){
                ResultBall(
                    ballText = topLeft,
                    ballSize = 30.dp,
                )
                Text(text = "")
                Text(text = "")
                Text(text = "")
                ResultBall(
                    ballText = topRight,
                    ballSize = 30.dp,
                )
            }
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .width(canvasWidth)
                    .height(cHeight)
            ){
                Text(text = "")
                Text(text = "")
                ResultBall(
                    ballText = top,
                    ballSize = 30.dp,
                )
                Text(text = "")
                Text(text = "")
            }
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .width(canvasWidth)
                    .height(cHeight)
            ){
                Text(text = "")
                ResultBall(
                    ballText = left,
                    ballSize = 30.dp,
                )
                Text(text = "")
                ResultBall(
                    ballText = right,
                    ballSize = 30.dp,
                )
                Text(text = "")
            }
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .width(canvasWidth)
                    .height(cHeight)
            ){
                Text(text = "")
                Text(text = "")
                ResultBall(
                    ballText = bottom,
                    ballSize = 30.dp,
                )
                Text(text = "")
                Text(text = "")
            }
            Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .width(canvasWidth)
                    .height(cHeight)
            ){
                ResultBall(
                    ballText = bottomLeft,
                    ballSize = 30.dp,
                )
                Text(text = "")
                Text(text = "")
                Text(text = "")
                ResultBall(
                    ballText = bottomRight,
                    ballSize = 30.dp,
                )
            }
        }

        Canvas(
            modifier = Modifier.width(cWidth*2.5f).height(cHeight*3.2f),
            onDraw = {
                drawLine(
                    color = Color.DarkGray,
                    strokeWidth = 1.dp.toPx(),
                    start = Offset(x = 45f, y = 45f),
                    end = Offset(x = size.width-45f, y = size.height-45f)
                )
                drawLine(
                    color = Color.DarkGray,
                    strokeWidth = 1.dp.toPx(),
                    start = Offset(x = 45f, y = size.height-45),
                    end = Offset(x = size.width-45, y = 45f)
                )
            }
        )
    }


}