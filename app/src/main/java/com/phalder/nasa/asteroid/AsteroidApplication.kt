package com.phalder.nasa.asteroid

import android.app.Application
import android.os.Build
import androidx.work.*
import com.phalder.nasa.asteroid.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setUpRecurringWork()
        }
    }

    private fun setUpRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        val periodicRequest  = PeriodicWorkRequestBuilder<RefreshDataWorker>(
                            1,
                            TimeUnit.DAYS)
                            .setConstraints(constraints)
                            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                                RefreshDataWorker.WORK_NAME,
                                ExistingPeriodicWorkPolicy.REPLACE,
                                periodicRequest
        )
    }
}