package com.edsusantoo.bismillah.forecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edsusantoo.bismillah.forecast.data.db.entity.CURRENT_WEATHER_ID
import com.edsusantoo.bismillah.forecast.data.db.entity.CurrentWeatherEntry
import com.edsusantoo.bismillah.forecast.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.edsusantoo.bismillah.forecast.data.db.unitlocalized.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    //mereplace data yang sudah ada
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query( "select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric():LiveData<MetricCurrentWeatherEntry>

    @Query( "select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial():LiveData<ImperialCurrentWeatherEntry>
}
