package ru.linew.todoapp.presentation.feature.adding.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
class YandexTodoColors(
    supportSeparator: Color,
    supportOverlay: Color,
    labelPrimary: Color,
    labelSecondary: Color,
    labelTertiary: Color,
    labelDisable: Color,
    colorRed: Color,
    colorGreen: Color,
    colorBlue: Color,
    colorGray: Color,
    colorGrayLight: Color,
    colorWhite: Color,
    backPrimary: Color,
    backSecondary: Color,
    backElevated: Color,
) {
    var supportSeparator by mutableStateOf(supportSeparator)
        private set
    var supportOverlay by mutableStateOf(supportOverlay)
        private set
    var labelPrimary by mutableStateOf(labelPrimary)
        private set
    var labelSecondary by mutableStateOf(labelSecondary)
        private set
    var labelTertiary by mutableStateOf(labelTertiary)
        private set
    var labelDisable by mutableStateOf(labelDisable)
        private set
    var colorRed by mutableStateOf(colorRed)
        private set
    var colorGreen by mutableStateOf(colorGreen)
        private set
    var colorBlue by mutableStateOf(colorBlue)
        private set
    var colorGray by mutableStateOf(colorGray)
        private set
    var colorGrayLight by mutableStateOf(colorGrayLight)
        private set
    var colorWhite by mutableStateOf(colorWhite)
        private set
    var backPrimary by mutableStateOf(backPrimary)
        private set
    var backSecondary by mutableStateOf(backSecondary)
        private set
    var backElevated by mutableStateOf(backElevated)
        private set

    fun update(other: YandexTodoColors) {
        supportSeparator = other.supportSeparator
        supportOverlay = other.supportOverlay
        labelPrimary = other.labelPrimary
        labelSecondary = other.labelSecondary
        labelTertiary = other.labelTertiary
        labelDisable = other.labelDisable
        colorRed = other.colorRed
        colorGreen = other.colorGreen
        colorBlue = other.colorBlue
        colorGray = other.colorGray
        colorGrayLight = other.colorGrayLight
        colorWhite = other.colorWhite
        backPrimary = other.backPrimary
        backSecondary = other.backSecondary
        backElevated = other.backElevated
    }

}

private val DarkColorScheme = YandexTodoColors(
    supportSeparator = supportDarkSeparator,
    supportOverlay = supportDarkOverlay,
    labelPrimary = labelDarkPrimary,
    labelSecondary = labelDarkSecondary,
    labelTertiary = labelDarkTertiary,
    labelDisable = labelDarkDisable,
    colorRed = colorDarkRed,
    colorGreen = colorDarkGreen,
    colorBlue = colorDarkBlue,
    colorGray = colorDarkGray,
    colorGrayLight = colorDarkGrayLight,
    colorWhite = colorDarkWhite,
    backPrimary = backDarkPrimary,
    backSecondary = backDarkSecondary,
    backElevated = backDarkElevated,
)


private val LightColorScheme = YandexTodoColors(
    supportSeparator = supportLightSeparator,
    supportOverlay = supportLightOverlay,
    labelPrimary = labelLightPrimary,
    labelSecondary = labelLightSecondary,
    labelTertiary = labelLightTertiary,
    labelDisable = labelLightDisable,
    colorRed = colorLightRed,
    colorGreen = colorLightGreen,
    colorBlue = colorLightBlue,
    colorGray = colorLightGray,
    colorGrayLight = colorLightGrayLight,
    colorWhite = colorLightWhite,
    backPrimary = backLightPrimary,
    backSecondary = backLightSecondary,
    backElevated = backLightElevated,
)

internal val LocalCustomColors =
    staticCompositionLocalOf<YandexTodoColors> { error("No colors provided") }



@Composable
fun ProvideYandexTodoColors(
    colors: YandexTodoColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(
        LocalCustomColors provides colorPalette,
        content = content
    )
}

object YandexTodoTheme {
    val colors: YandexTodoColors
        @Composable
        get() = LocalCustomColors.current

}

@Composable
fun YandexTodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    ProvideYandexTodoColors(colors = colorScheme) {
        MaterialTheme(
            content = content
        )
    }
}