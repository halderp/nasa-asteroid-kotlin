package com.phalder.nasa.asteroid.asteroidlist

import android.app.Application
import androidx.lifecycle.*
import com.phalder.nasa.asteroid.database.Asteroid
import com.phalder.nasa.asteroid.database.AsteroidDatabase
import com.phalder.nasa.asteroid.network.*
import com.phalder.nasa.asteroid.repository.AsteroidRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AsteroidListViewModel(application: Application): AndroidViewModel(application) {

    // constants definitions
    val NASA_API_KEY = "89JXbvTAyn6PdbBhlz7L5u0HGWTRuBaOwqiI9AYT"

    // Database and Repository
    private val asteroidDatabase = AsteroidDatabase.getInstance(application)
    private val asteroidRepository = AsteroidRepository(asteroidDatabase)

    // The internal MutableLiveData data & the public LiveData to capture Picture of Day
    private val _apodImgUrl = MutableLiveData<String>()
    val apodImgUrl : LiveData<String>
        get() =_apodImgUrl

    // Livedata for Neo Feed/Asteroid List
    val neoFeedList = asteroidRepository.asteroids

    // initializer block
    init {
        setDefaultPicOfTheDay()
        getAstronomyPicOfTheDay()
        getNeoFeedsForSevenDays()
    }

    private fun setDefaultPicOfTheDay() {
        // set the default picture so that user does not have to see a loading indicator
        _apodImgUrl.value = ""
    }

    private fun getAstronomyPicOfTheDay() {
        // date should be of format : YYYY-MM-DD
        val sevenDaysList = getNextSevenDaysFormattedDates()
        val queryParamMap = mapOf("api_key" to NASA_API_KEY,"date" to sevenDaysList.get(0))
        viewModelScope.launch {
            try {
                val apodModel = NasaApi.retrofitAPODService.getAstronomyPicOfDay(queryParamMap)
                // Update the image view only when the media type == image . else it will show the default image
                if (apodModel.media_type.equals("image",true))
                    _apodImgUrl.value = apodModel.sdImgSrcUrl
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        }
    }
    private fun getNeoFeedsForSevenDays() {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

}