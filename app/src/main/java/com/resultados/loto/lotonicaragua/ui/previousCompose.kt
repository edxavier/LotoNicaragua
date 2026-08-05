package com.resultados.loto.lotonicaragua.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.data.LotoResult
import com.resultados.loto.lotonicaragua.ui.ads.NativeAdCard
import com.resultados.loto.lotonicaragua.ui.home.composes.CardTopAccent
import com.resultados.loto.lotonicaragua.ui.home.composes.ResultBall

private val AdMarker = Any()

@Composable
fun PreviousResults(results: List<LotoResult>) {
    val displayItems = remember(results) {
        buildList {
            results.forEachIndexed { index, result ->
                add(result as Any)
                // Show ad every 6 items
                if ((index + 1) % 6 == 0 && index < results.size - 1) {
                    add(AdMarker)
                }
            }
            // Add ad at the end if the list isn't empty and the last item wasn't an ad
            if (results.isNotEmpty() && results.size % 6 != 0) {
                add(AdMarker)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(displayItems.size) { i ->
            val item = displayItems[i]
            when {
                item === AdMarker -> NativeAdCard()
                item is LotoResult -> PreviousResult(item)
            }
        }
    }
}

@Composable
fun PreviousResult(result: LotoResult) {
    val isDark = isSystemInDarkTheme()
    val accent = gameAccentColor(isDark, result.game)
    val surface = gameSurfaceColor(isDark, result.game)
    val secondaryAccent = if (isDark) orangeDark else orangeLight
    val secondaryContent = if (isDark) Color(0xFFFFCC80) else Color(0xFFE65100)

    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = surface)
    ) {
        Column {
            CardTopAccent(accent)
            Column(Modifier.padding(18.dp)) {
                if (result.game == ScraperHelper.LAGRANDE) {
                    Text(result.date.replace('|', '\n'), modifier = Modifier.fillMaxWidth(), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = accent)
                    Spacer(Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ResultBall(result.result1.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result2.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result3.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result4.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result5.toString(), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                        ResultBall(result.result6.toString(), borderColor = Color(0xFFFFD700), ballSize = 40.dp, textSize = 14.sp, contentColor = Color(0xFFB8860B))
                    }
                } else {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(result.date.replace('|', '\n'), Modifier.weight(1f), fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = accent)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            ResultBall(result.result1.toString().padStart(2, '0'), borderColor = accent, ballSize = 40.dp, textSize = 14.sp, contentColor = accent)
                            if (result.result2 >= 0) ResultBall(result.result2.toString(), borderColor = secondaryAccent, ballSize = 40.dp, textSize = 12.sp, contentColor = secondaryContent)
                            if (result.month.isNotEmpty()) ResultBall(result.month, borderColor = secondaryAccent, ballSize = 40.dp, textSize = 12.sp, contentColor = secondaryContent)
                            if (result.multix.isNotEmpty()) ResultBall(result.multix, borderColor = secondaryAccent, ballSize = 40.dp, textSize = 12.sp, contentColor = secondaryContent)
                            if (result.result3 >= 0) ResultBall(result.result3.toString(), borderColor = secondaryAccent, ballSize = 40.dp, textSize = 12.sp, contentColor = secondaryContent)
                        }
                    }
                }
            }
        }
    }
}
