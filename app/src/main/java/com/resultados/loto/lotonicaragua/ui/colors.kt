package com.resultados.loto.lotonicaragua.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.resultados.loto.lotonicaragua.ScraperHelper
import com.resultados.loto.lotonicaragua.ui.theme.LotoGame
import com.resultados.loto.lotonicaragua.ui.theme.gameAccent
import com.resultados.loto.lotonicaragua.ui.theme.gameSurface

fun scraperGameToLotoGame(game: Int): LotoGame = when (game) {
    ScraperHelper.LAGRANDE -> LotoGame.LAGRANDE
    ScraperHelper.FECHAS -> LotoGame.FECHAS
    ScraperHelper.JUEGA3 -> LotoGame.JUEGA3
    ScraperHelper.JUEGA4 -> LotoGame.JUEGA4
    ScraperHelper.SUPERCOMBO -> LotoGame.SUPERCOMBO
    ScraperHelper.TERMINACION2 -> LotoGame.TERMINACION2
    else -> LotoGame.DIARIA
}

@Composable
fun gameAccentColor(isDark: Boolean, game: Int): Color = gameAccent(isDark, scraperGameToLotoGame(game))

@Composable
fun gameSurfaceColor(isDark: Boolean, game: Int): Color = gameSurface(isDark, scraperGameToLotoGame(game))

val goldDark = Color(0xFFFFD700)
val goldLight = Color(0xFFB8860B)
val orangeDark = Color(0xFFFFB74D)
val orangeLight = Color(0xFFE65100)

val ballGray = listOf(Color(0xFFFAFAFA), Color(0xFFEEEEEE), Color(0xFFE0E0E0))
val ballGold = listOf(Color(0xFFFFF8E1), Color(0xFFFFE082), Color(0xFFFFD54F))
val ballOrange = listOf(Color(0xFFFFE0B2), Color(0xFFFFCC80), Color(0xFFFFB74D))
val ballWhite = listOf(Color(0xFFFFFFFF), Color(0xFFF5F5F5), Color(0xFFEEEEEE))

val yellowGradient = listOf(Color(0xFFFFF9C4), Color(0xFFFBC02D))
val orangeGradient = listOf(Color(0xFFFFE0B2), Color(0xFFFFA000))
val grayGradient = ballGray
val lightGreenGradient = listOf(Color(0xFF81C784), Color(0xFF43A047))
val greenGradient = listOf(Color(0xFF2E7D32), Color(0xFF1B5E20))
val deepOrangeGradient = listOf(Color(0xFFFF7043), Color(0xFFE64A19))
val cyanGradient = listOf(Color(0xFF26C6DA), Color(0xFF0097A7))
val purpleGradient = listOf(Color(0xFFAB47BC), Color(0xFF8E24AA))
val pinkGradient = listOf(Color(0xFFF06292), Color(0xFFD81B60))
val blueGradient = listOf(Color(0xFF42A5F5), Color(0xFF1E88E5))
val goldGradient = listOf(Color(0xFFFFCA28), Color(0xFFFF8F00))
val messageGradient = listOf(Color(0xFFEDE7E3), Color(0xFFDBD2CC))
val titleMessageGradient = listOf(Color(0xFFB8A99A), Color(0xFFA89888))
