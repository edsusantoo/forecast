package com.edsusantoo.bismillah.forecast.data

import com.edsusantoo.bismillah.forecast.data.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "f5f5565619e54ccd8dc31354191301"

//http://api.apixu.com/v1/current.json?key=f5f5565619e54ccd8dc31354191301&q=Jakarta&Lang=id

interface ApixuWeatherApiService {

    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>
}