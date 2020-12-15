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
    val NASA_API_KEY = "89JXbvTAyn6PdbBhlz7L5u0HGWTRuBaOwqiI9AYT"

    val asteroids : LiveData<List<Asteroid>> = database.asteroidDatabaseDao.getAllSortedAsteroids()

    // This function will make a call to network to get the Asteroid data and save it in database
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO){
            val sevenDaysList = getNextSevenDaysFormattedDates()
            val startIndx = 0;
            val endIndx = startIndx + 7;
            val queryParamMap = mapOf("start_date" to sevenDaysList.get(startIndx),"end_date" to sevenDaysList.get(endIndx), "api_key" to NASA_API_KEY)
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
}