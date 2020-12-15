package com.phalder.nasa.asteroid.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.phalder.nasa.asteroid.database.AsteroidDatabase
import com.phalder.nasa.asteroid.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext,params){
    override suspend fun doWork(): Result {
        // Database and Repository
        val asteroidDatabase = AsteroidDatabase.getInstance(applicationContext)
        val asteroidRepository = AsteroidRepository(asteroidDatabase)

        // try to get the Data for next 7 days.
        // after that delete old data
        // if failed then retry
        return try {
            asteroidRepository.refreshAsteroids()
            asteroidRepository.deleteOldData()
            Result.success()
        }
        catch (e: HttpException){
            Result.retry()
        }
    }
    companion object {
        const val WORK_NAME = "NeoRefreshDataWorker"
    }
}