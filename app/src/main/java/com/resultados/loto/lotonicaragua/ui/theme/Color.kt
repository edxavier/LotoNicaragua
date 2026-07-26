package com.resultados.loto.lotonicaragua.ui.theme

import androidx.compose.ui.graphics.Color

// ─── Light Color Scheme ─────────────────────────────────────────
val LightPrimary = Color(0xFFB86100)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = Color(0xFFFFDCC2)
val LightOnPrimaryContainer = Color(0xFF3B1A00)
val LightSecondary = Color(0xFF3F51B5)
val LightOnSecondary = Color(0xFFFFFFFF)
val LightSecondaryContainer = Color(0xFFDDE0FF)
val LightOnSecondaryContainer = Color(0xFF001354)
val LightTertiary = Color(0xFF5D6135)
val LightOnTertiary = Color(0xFFFFFFFF)
val LightTertiaryContainer = Color(0xFFE2E6AE)
val LightOnTertiaryContainer = Color(0xFF191E00)
val LightError = Color(0xFFBA1A1A)
val LightErrorContainer = Color(0xFFFFDAD6)
val LightBackground = Color(0xFFFFF8F6)
val LightOnBackground = Color(0xFF231A14)
val LightSurface = Color(0xFFFFF8F6)
val LightOnSurface = Color(0xFF231A14)
val LightSurfaceVariant = Color(0xFFF5DFD1)
val LightOnSurfaceVariant = Color(0xFF53443A)
val LightOutline = Color(0xFF857468)
val LightOutlineVariant = Color(0xFFD7C2B4)

// ─── Dark Color Scheme ──────────────────────────────────────────
val DarkPrimary = Color(0xFFFFB871)
val DarkOnPrimary = Color(0xFF623000)
val DarkPrimaryContainer = Color(0xFF8B4800)
val DarkOnPrimaryContainer = Color(0xFFFFDCC2)
val DarkSecondary = Color(0xFFB9C2FF)
val DarkOnSecondary = Color(0xFF052186)
val DarkSecondaryContainer = Color(0xFF25369C)
val DarkOnSecondaryContainer = Color(0xFFDDE0FF)
val DarkTertiary = Color(0xFFC6CA94)
val DarkOnTertiary = Color(0xFF2F330C)
val DarkTertiaryContainer = Color(0xFF454A20)
val DarkOnTertiaryContainer = Color(0xFFE2E6AE)
val DarkError = Color(0xFFFFB4AB)
val DarkErrorContainer = Color(0xFF93000A)
val DarkBackground = Color(0xFF1B110C)
val DarkOnBackground = Color(0xFFF1DFD3)
val DarkSurface = Color(0xFF1B110C)
val DarkOnSurface = Color(0xFFF1DFD3)
val DarkSurfaceVariant = Color(0xFF53443A)
val DarkOnSurfaceVariant = Color(0xFFD7C2B4)
val DarkOutline = Color(0xFFA08D7F)
val DarkOutlineVariant = Color(0xFF53443A)

// ─── Game accents ───────────────────────────────────────────────
enum class LotoGame(val key: Int) {
    DIARIA(0),
    FECHAS(1),
    JUEGA3(2),
    JUEGA4(3),
    SUPERCOMBO(4),
    TERMINACION2(5),
    LAGRANDE(6),
    NONE(99)
}

data class GameColors(
    val accent: Color,
    val accentLight: Color,
    val surface: Color,
    val surfaceDark: Color
)

val gameColorMap = mapOf(
    LotoGame.DIARIA to GameColors(
        accent = Color(0xFF2E7D32),
        accentLight = Color(0xFF81C784),
        surface = Color(0xFFF4F8F1),
        surfaceDark = Color(0xFF1A231A)
    ),
    LotoGame.FECHAS to GameColors(
        accent = Color(0xFFD84315),
        accentLight = Color(0xFFFF8A65),
        surface = Color(0xFFFFF7F3),
        surfaceDark = Color(0xFF251A16)
    ),
    LotoGame.JUEGA3 to GameColors(
        accent = Color(0xFF00838F),
        accentLight = Color(0xFF4DD0E1),
        surface = Color(0xFFF1F9FA),
        surfaceDark = Color(0xFF162325)
    ),
    LotoGame.JUEGA4 to GameColors(
        accent = Color(0xFFAD1457),
        accentLight = Color(0xFFF48FB1),
        surface = Color(0xFFFEF4F8),
        surfaceDark = Color(0xFF26161E)
    ),
    LotoGame.SUPERCOMBO to GameColors(
        accent = Color(0xFF6A1B9A),
        accentLight = Color(0xFFCE93D8),
        surface = Color(0xFFF8F4FC),
        surfaceDark = Color(0xFF1E1728)
    ),
    LotoGame.TERMINACION2 to GameColors(
        accent = Color(0xFF1565C0),
        accentLight = Color(0xFF64B5F6),
        surface = Color(0xFFF1F6FC),
        surfaceDark = Color(0xFF141C29)
    ),
    LotoGame.LAGRANDE to GameColors(
        accent = Color(0xFFF57F17),
        accentLight = Color(0xFFFFD54F),
        surface = Color(0xFFFFFCF2),
        surfaceDark = Color(0xFF261E0A)
    ),
    LotoGame.NONE to GameColors(
        accent = Color(0xFF795548),
        accentLight = Color(0xFFBCAAA4),
        surface = Color(0xFFF8F6F5),
        surfaceDark = Color(0xFF1E1C1B)
    )
)

fun gameColors(isDark: Boolean, game: LotoGame): GameColors {
    val base = gameColorMap[game] ?: gameColorMap[LotoGame.NONE]!!
    return if (isDark) base.copy(accent = base.accentLight) else base
}

fun gameAccent(isDark: Boolean, game: LotoGame): Color =
    if (isDark) (gameColorMap[game] ?: gameColorMap[LotoGame.NONE]!!).accentLight
    else (gameColorMap[game] ?: gameColorMap[LotoGame.NONE]!!).accent

fun gameSurface(isDark: Boolean, game: LotoGame): Color =
    if (isDark) (gameColorMap[game] ?: gameColorMap[LotoGame.NONE]!!).surfaceDark
    else (gameColorMap[game] ?: gameColorMap[LotoGame.NONE]!!).surface
