package com.edsusantoo.bismillah.forecast.data.repository

import androidx.lifecycle.LiveData
import com.edsusantoo.bismillah.forecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric:Boolean):LiveData<out UnitSpecificCurrentWeatherEntry>
}