package ru.linew.todoapp.presentation.feature.adding.ui.compose.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val Typography.largeTitle: TextStyle
@Composable
get() = TextStyle(
    fontSize = 32.sp,
    lineHeight = 38.sp,
    color = YandexTodoTheme.colors.labelPrimary
)
val Typography.title: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 20.sp,
        lineHeight = 32.sp,
        color = YandexTodoTheme.colors.labelPrimary
    )
val Typography.buttonText: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 14.sp,
        lineHeight = 24.sp,
        color = YandexTodoTheme.colors.colorBlue
    )
val Typography.body: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        color = YandexTodoTheme.colors.labelPrimary
    )
val Typography.subhead: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = YandexTodoTheme.colors.labelTertiary
    )