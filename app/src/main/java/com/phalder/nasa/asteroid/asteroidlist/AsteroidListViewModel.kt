package com.phalder.nasa.asteroid.asteroidlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phalder.nasa.asteroid.network.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AsteroidListViewModel: ViewModel() {

    // constants definitions
    val NASA_API_KEY = "89JXbvTAyn6PdbBhlz7L5u0HGWTRuBaOwqiI9AYT"

    // The internal MutableLiveData data & the public LiveData to capture Picture of Day
    private val _apodImgUrl = MutableLiveData<String>()
    val apodImgUrl : LiveData<String>
        get() =_apodImgUrl

    // Livedata for Neo Feed List
    private val _neoFeedList = MutableLiveData<List<Asteroid>>()
    val neoFeedList : LiveData<List<Asteroid>>
        get() = _neoFeedList

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
        // date should be of format : YYYY-MM-DD
        val sevenDaysList = getNextSevenDaysFormattedDates()
        val startIndx = 0;
        val endIndx = startIndx + 7;
        val queryParamMap = mapOf("start_date" to sevenDaysList.get(startIndx),"end_date" to sevenDaysList.get(endIndx), "api_key" to NASA_API_KEY)
        viewModelScope.launch {
            try {
                val response = NasaApi.retrofitFeedService.getNeoFeeds(queryParamMap)
                val json = JSONObject(response.body())
                _neoFeedList.value = parseAsteroidsJsonResult(json)
                Timber.d(neoFeedList.value?.size.toString())
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        }
    }

    private fun getTodaysDate(): String {
        var date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val answer:String =  formatter.format(date)
        return answer
    }

}