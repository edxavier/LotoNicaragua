package com.resultados.loto.lotonicaragua.ui.home.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardNoData(title: String, description: String) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(3.dp).background(Color(0xFFBCAAA4)))
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Icon(Icons.Default.Warning, null, tint = Color(0xFF795548), modifier = Modifier.size(20.dp))
                Column {
                    Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5D4037))
                    Text(description, fontSize = 13.sp, color = Color(0xFF9E9E9E))
                }
            }
        }
    }
}
