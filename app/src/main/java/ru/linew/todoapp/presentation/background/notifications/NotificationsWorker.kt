package ru.linew.todoapp.presentation.background.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.linew.todoapp.R
import ru.linew.todoapp.presentation.activity.MainActivity
import ru.linew.todoapp.presentation.background.repository.DeadlineTodoProvider
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.repository.TodoItemsRepository
import ru.linew.todoapp.shared.Constants

class NotificationsWorker constructor(
    private val context: Context,
    workerParams: WorkerParameters,
     val repository: TodoItemsRepository
) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        val applicationContext = context.applicationContext
        val deadlineTodoProvider = (repository as DeadlineTodoProvider)
        val deadlineItems: List<TodoItem>
        return try {
            deadlineItems = deadlineTodoProvider.getDeadlineTodoList(System.currentTimeMillis())
            sendNotifications(applicationContext, notificationManager, deadlineItems)
            Result.success()
        } catch (e: Exception){
            Result.retry()
        }
    }

    private fun sendNotifications(
        applicationContext: Context,
        notificationManager: NotificationManager,
        todoItems: List<TodoItem>,
    ) {
        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            contentIntent,
            PendingIntent.FLAG_MUTABLE
        )
        todoItems.forEach {
            val notification =
                NotificationCompat.Builder(applicationContext, Constants.NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(it.body)
                    .setContentText(it.priority.toText(applicationContext))
                    .setSmallIcon(R.drawable.high_priority)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            notificationManager.notify(it.id.hashCode(), notification.build())
        }
    }

}

fun Priority.toText(context: Context): String =
    when (this) {
        Priority.LOW -> context.getString(R.string.low)
        Priority.NO -> context.getString(R.string.no)
        Priority.HIGH -> context.getString(R.string.high)
    }
