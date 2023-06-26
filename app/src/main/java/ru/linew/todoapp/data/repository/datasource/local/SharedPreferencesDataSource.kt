package ru.linew.todoapp.data.repository.datasource.local

interface SharedPreferencesDataSource {
    fun setCurrentRevision(currentRevision: Int)
    fun getLocalCurrentRevision(): Int
    fun incrementCurrentRevision()
    fun flagNeedSyncUp()
    fun flagNeedSyncDown()
    fun getFlagNeedSyncState(): Boolean
    fun getDeviceId(): String
}