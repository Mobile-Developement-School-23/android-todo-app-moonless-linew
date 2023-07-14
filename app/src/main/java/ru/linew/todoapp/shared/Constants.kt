package ru.linew.todoapp.shared



object Constants {
    const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
    const val SHARED_PREFERENCES_NAME = "MAIN"
    const val SHARED_PREFERENCES_REVISION_KEY = "revision"
    const val SHARED_PREFERENCES_SYNC_FLAG_KEY = "update_flag"
    const val SHARED_PREFERENCES_UNIQUE_ID_KEY = "device_id"
    const val SHARED_PREFERENCES_THEME_KEY = "theme"
    const val SHARED_PREFERENCES_THEME_DEFAULT = "SYSTEM"
    const val SHARED_PREFERENCES_NOTIFICATIONS_KEY = "notifications"

    const val NOTIFICATION_CHANNEL_ID = "todo_app_notification"
    const val NOTIFICATION_CHANNEL_NAME = "Deadline notifier"
    const val NOTIFICATION_WORKER_TAG = "notifications_worker"

    const val MILLIS_IN_DAY = 86400000
    const val UTC_3_OFFSET = 10800000

    const val BACKGROUND_WORK_NAME = "ListUpdater"
    const val REPEAT_INTERVAL_IN_HOURS = 8L


    const val TODO_ID_ARGUMENT_KEY = "id"
}
