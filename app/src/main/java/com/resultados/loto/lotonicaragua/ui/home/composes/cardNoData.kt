package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.api.models.diaria.DiariaResult
import com.resultados.loto.lotonicaragua.ui.greenGradient
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections
import com.resultados.loto.lotonicaragua.ui.lightGreenGradient
import com.resultados.loto.lotonicaragua.ui.messageGradient
import com.resultados.loto.lotonicaragua.ui.orangeGradient
import com.resultados.loto.lotonicaragua.ui.titleMessageGradient
import com.resultados.loto.lotonicaragua.ui.yellowGradient

@Composable
fun CardNoData(
    title: String,
    description: String
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = messageGradient
                )
            ),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(colors = titleMessageGradient))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 18.sp, color = Color.White)
        }

        Column (
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = description,  fontSize = 14.sp,)
        }

    }
}
