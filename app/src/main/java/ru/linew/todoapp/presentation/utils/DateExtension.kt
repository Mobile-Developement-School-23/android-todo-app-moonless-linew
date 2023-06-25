package ru.linew.todoapp.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateFormat(): String{
    return SimpleDateFormat("dd MMMM yyyy", Locale("ru")).format(Date(this))
}