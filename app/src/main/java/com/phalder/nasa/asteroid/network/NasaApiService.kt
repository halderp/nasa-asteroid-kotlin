package com.phalder.nasa.asteroid.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
 * Enable logging to see request/response body
 */
private val clientAPOD = OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

private val retrofitAPOD = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(clientAPOD)
            .baseUrl(BASE_URL_APOD)
            .build()

interface NasaAPODApiService {
    //GET https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&date=YYYY-MM-DD
    @GET("apod")
    suspend fun getAstronomyPicOfDay(@QueryMap query: Map<String, String> ): APODModel
}

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi for Feed Endpoint
 */
private val clientFeed = OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()
private val retrofitFeed = Retrofit.Builder()
        .client(clientFeed)
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL_FEED)
        .build()

interface NasaFeedApiService {
    // GET https://api.nasa.gov/neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key=API_KEY
    @GET("feed")
    suspend fun getNeoFeeds(@QueryMap query: Map<String, String> ): Response<String>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service. This will be used by other parts of the application
 */
object NasaApi {
    val retrofitAPODService : NasaAPODApiService by lazy { retrofitAPOD.create(NasaAPODApiService::class.java) }
    val retrofitFeedService : NasaFeedApiService by lazy { retrofitFeed.create(NasaFeedApiService::class.java) }
}

