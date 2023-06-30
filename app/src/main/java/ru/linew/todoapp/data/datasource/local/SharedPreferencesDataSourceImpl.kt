package ru.linew.todoapp.data.datasource.local

import android.content.SharedPreferences
import ru.linew.todoapp.data.repository.datasource.local.SharedPreferencesDataSource
import ru.linew.todoapp.shared.Constants
import javax.inject.Inject

class SharedPreferencesDataSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SharedPreferencesDataSource {
    override fun setCurrentRevision(currentRevision: Int) =
        sharedPreferences.edit().putInt(Constants.SHARED_PREFERENCES_REVISION_KEY, currentRevision)
            .apply()


    override fun getLocalCurrentRevision(): Int = sharedPreferences.getInt(Constants.SHARED_PREFERENCES_REVISION_KEY, 0)


    override fun incrementCurrentRevision() {
        val current = getLocalCurrentRevision()
        setCurrentRevision(current + 1)
    }

    override fun flagNeedSyncUp() =
        sharedPreferences.edit().putBoolean(Constants.SHARED_PREFERENCES_SYNC_FLAG_KEY, true)
            .apply()


    override fun flagNeedSyncDown() =
        sharedPreferences.edit().putBoolean(Constants.SHARED_PREFERENCES_SYNC_FLAG_KEY, false)
            .apply()


    override fun getFlagNeedSyncState(): Boolean =
        sharedPreferences.getBoolean(Constants.SHARED_PREFERENCES_SYNC_FLAG_KEY, false)

    override fun getDeviceId(): String =
        sharedPreferences.getString(Constants.SHARED_PREFERENCES_UNIQUE_ID_KEY, "null")!!



}