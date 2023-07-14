package ru.linew.todoapp.presentation.background.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.linew.todoapp.data.model.exception.TodoSyncFailed
import ru.linew.todoapp.presentation.repository.TodoItemsRepository

class BackgroundWorkerClass constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: TodoItemsRepository
) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            repository.syncLocalListOfTodo()
            Result.success()
        } catch (e: TodoSyncFailed){
            Result.retry()
        }
    }
}
