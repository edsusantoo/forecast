package com.edsusantoo.bismillah.forecast.data.network

import androidx.lifecycle.LiveData
import com.edsusantoo.bismillah.forecast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location:String,
        languageCode:String
    )
}