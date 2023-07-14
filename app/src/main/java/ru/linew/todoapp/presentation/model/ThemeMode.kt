package ru.linew.todoapp.presentation.model

enum class ThemeMode {
    LIGHT, DARK, SYSTEM;

    fun toInt(): Int =
        when(this){
            ThemeMode.LIGHT -> 0
            ThemeMode.DARK -> 1
            ThemeMode.SYSTEM -> 2
        }
}


