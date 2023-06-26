package ru.linew.todoapp.presentation.feature.adding.viewmodel.state


sealed class Result<out T: Any>{
    object Loading : Result<Nothing>()
    data class Error(val exception: Exception): Result<Nothing>()
    class Complete<out T: Any>(val result: T): Result<T>()
}
