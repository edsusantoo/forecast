package com.edsusantoo.bismillah.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.edsusantoo.bismillah.forecast.data.repository.ForecastRepository
import com.edsusantoo.bismillah.forecast.utils.UnitSystem
import com.edsusantoo.bismillah.forecast.utils.lazyDeffered

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC
    val isMetric: Boolean
        get() = unitSystem==UnitSystem.METRIC
    val weather by lazyDeffered {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
