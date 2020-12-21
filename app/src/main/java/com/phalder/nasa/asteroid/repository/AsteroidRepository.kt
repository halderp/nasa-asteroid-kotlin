package com.phalder.nasa.asteroid.repository

import androidx.lifecycle.LiveData
import com.phalder.nasa.asteroid.database.Asteroid
import com.phalder.nasa.asteroid.database.AsteroidDatabase
import com.phalder.nasa.asteroid.network.NasaApi
import com.phalder.nasa.asteroid.network.getNextSevenDaysFormattedDates
import com.phalder.nasa.asteroid.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AsteroidRepository(private val database: AsteroidDatabase) {
    val NASA_API_KEY = "DEMO_KEY"

    // Date Range related properties
    val sevenDaysList = getNextSevenDaysFormattedDates()
    var starDate: String = sevenDaysList.get(0)
    var endDate : String = sevenDaysList.get(7)

    val asteroids : LiveData<List<Asteroid>> = database.asteroidDatabaseDao.getAllSortedAsteroids(starDate,endDate)

    // This function will make a call to network to get the Asteroid data and save it in database
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO){

            val queryParamMap = mapOf("start_date" to starDate,"end_date" to endDate, "api_key" to NASA_API_KEY)
            try {
                val response = NasaApi.retrofitFeedService.getNeoFeeds(queryParamMap)
                val json = JSONObject(response.body())
                val asteroidlst = parseAsteroidsJsonResult(json)
                Timber.d("New data recieved")
                database.asteroidDatabaseDao.insertAllAsteroids(*asteroidlst.toTypedArray())
            }
            catch (e: Exception) {
                Timber.e(e.message)
            }


        }
    }

    suspend fun deleteOldData()
    {
        // Delete all old data . For safety the delete query will delete all the data from previous days
        withContext(Dispatchers.Default) {
            // delete the records
            database.asteroidDatabaseDao.deleteRecords(starDate)
        }

    }
}