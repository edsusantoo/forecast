package com.edsusantoo.bismillah.forecast.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.edsusantoo.bismillah.forecast.data.db.CurrentWeatherDao
import com.edsusantoo.bismillah.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.edsusantoo.bismillah.forecast.data.network.WeatherNetworkDataSource
import com.edsusantoo.bismillah.forecast.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao:CurrentWeatherDao,
    private val weatherNetworkDataSource:WeatherNetworkDataSource
) : ForecastRepository {
    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }
    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather:CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather("Los Angles",
            Locale.getDefault().language
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNeeded(lastFetchtime:ZonedDateTime):Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchtime.isBefore(thirtyMinutesAgo)
    }
}