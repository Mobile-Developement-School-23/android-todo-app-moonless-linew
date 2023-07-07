package ru.linew.todoapp.presentation.background

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import javax.inject.Inject

class BackgroundWorkerFactory @Inject constructor(private val repository: TodoItemsRepository): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return BackgroundWorkerClass(appContext, workerParameters, repository)
    }
}
