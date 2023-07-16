package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.ui.home.ResultsFragmentDirections

@Composable
fun CruzPiramideOptions(
    onClick:()->Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Row(modifier = Modifier.padding(bottom = 12.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(
                onClick = {
                   onClick()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C27B0)),
                border = BorderStroke(Dp(0f), color = Color.Transparent),
                shape = RoundedCornerShape(23.dp),
            ) {
                Icon(imageVector = Icons.Outlined.Info, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "PIRAMIDE + CRUZ de la Suerte", color = Color.White, fontSize = 11.sp)
            }

        }
    }
}