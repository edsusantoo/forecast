package com.edsusantoo.bismillah.forecast.ui.weather.current

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.edsusantoo.bismillah.forecast.R
import com.edsusantoo.bismillah.forecast.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory) //ini buat menghubungkan kodeinnya
            .get(CurrentWeatherViewModel::class.java)

        bindUI()

    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer //jika observer null maka akan merequestnya kembali

            group_loading.visibility = View.GONE

            updateLocation("Jakarta")
            updateDateToToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection, it.windSpeed)
            updateVisibility(it.visibilityDistance)

            Glide.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(img_condition_icon)

        })
    }

    private fun chooseLocalizedUnitAbbrevation(metric:String,imperial:String):String{
        return if(viewModel.isMetric) metric else imperial
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    @SuppressLint("SetTextI18n")
    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbrevation("°C","°F")
        txt_temperature.text="$temperature$unitAbbreviation"
        txt_feels_like_temperature.text="Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition:String){
        txt_condition.text=condition
    }

    @SuppressLint("SetTextI18n")
    private fun updatePrecipitation(precipitationVolume:Double){
        val unitAbbreviation = chooseLocalizedUnitAbbrevation("mm","in")
        txt_precipitation.text="Precipitaion: $precipitationVolume $unitAbbreviation"

    }

    @SuppressLint("SetTextI18n")
    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbrevation("kph", "mph")
        txt_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    @SuppressLint("SetTextI18n")
    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbrevation("km", "mi.")
        txt_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

}
