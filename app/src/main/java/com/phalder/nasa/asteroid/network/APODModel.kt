package com.phalder.nasa.asteroid.network


import androidx.lifecycle.LiveData
import com.squareup.moshi.Json


/**
 * Data Class for Storing the APOD response from NASA API:  Astronomy Picture of the Day.
 * This will be created and filled up by Retrofit on successful call to the API
 * We would be interested only in 2 Properties - hdurl => The high definition image url, url => standard definition image url
 * We will use the hdImgSrcUrl to display the Picture of the Day in Starting Fragment using Glide/Picasso
 * We will ignore rest of the properties as we do not want to display them in the UI or process them for any logic
 */

data class APODModel (
        val copyright : String ="", // Copyright attribute missing sometimes in JSON response. Defaulting it to empty string
        val date : String,
        val explanation : String,
        @Json(name = "hdurl") val hdImgSrcUrl : String,
        val media_type : String,
        val service_version : String,
        val title : String,
        @Json(name = "url") val sdImgSrcUrl : String
)