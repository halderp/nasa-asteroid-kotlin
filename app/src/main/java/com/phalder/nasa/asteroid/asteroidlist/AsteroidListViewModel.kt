package com.phalder.nasa.asteroid.asteroidlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phalder.nasa.asteroid.network.APODModel
import com.phalder.nasa.asteroid.network.NasaApi
import kotlinx.coroutines.launch
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

    // initializer block
    init {
        setDefaultPicOfTheDay()
        getAstronomyPicOfTheDay()
    }

    private fun setDefaultPicOfTheDay() {
        // set the default picture so that user does not have to see a loading indicator
        _apodImgUrl.value = ""
    }

    private fun getAstronomyPicOfTheDay() {
        // date should be of format : YYYY-MM-DD
        val queryParamMap = mapOf("api_key" to NASA_API_KEY,"date" to getTodaysDate())
        viewModelScope.launch {
            try {
                val apodModel = NasaApi.retrofitAPODService.getAstronomyPicOfDay(queryParamMap)
                _apodImgUrl.value = apodModel.sdImgSrcUrl
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