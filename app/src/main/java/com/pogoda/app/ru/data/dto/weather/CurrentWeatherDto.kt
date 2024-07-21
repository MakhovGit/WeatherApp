package com.pogoda.app.ru.data.dto.weather

import com.google.gson.annotations.SerializedName
import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.ZERO

data class CurrentWeatherDto(
    @field:SerializedName("time")
    val time: String = String.EMPTY,

    @field:SerializedName("interval")
    val interval: String = String.EMPTY,

    @field:SerializedName("temperature_2m")
    val temperature2m: Float = Float.ZERO,

    @field:SerializedName("apparent_temperature")
    val apparentTemperature: Float = Float.ZERO,

    @field:SerializedName("is_day")
    val isDay: Int = Int.ZERO,

    @field:SerializedName("weather_code")
    val weatherCode: Int = Int.ZERO

)