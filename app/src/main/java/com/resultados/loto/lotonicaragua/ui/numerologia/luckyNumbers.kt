package com.resultados.loto.lotonicaragua.ui.numerologia

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LuckyNumbers() {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val luckyDateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
    val currentDate = sdf.format(Date())
    val luckyDate = luckyDateFormat.format(Date())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        item{
            Text(
                text = "PIRAMIDE + CRUZ de la Suerte para el $currentDate",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
        }
        item { 
            Piramide(luckyDate = luckyDate)
        }
    }
}