package com.phalder.nasa.asteroid.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


// Base URL for APOD
private const val BASE_URL_APOD = "https://api.nasa.gov/planetary/"
// Base URL for Asteroids Feed
private const val  BASE_URL_FEED = "https://api.nasa.gov/neo/rest/v1/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi for APOD Endpoint
 */
private val retrofitAPOD = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_APOD)
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi for Feed Endpoint
 */
private val retrofitFeed = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_FEED)
    .build()

interface NasaAPODApiService {

    @GET("apod")
    suspend fun getAstronomyPicOfDay(@QueryMap query: Map<String, String> ): APODModel
}
/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object NasaApi {
    val retrofitAPODService : NasaAPODApiService by lazy { retrofitAPOD.create(NasaAPODApiService::class.java) }
}
