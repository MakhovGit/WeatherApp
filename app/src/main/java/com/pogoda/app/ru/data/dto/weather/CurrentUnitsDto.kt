package com.pogoda.app.ru.data.dto.weather

import com.google.gson.annotations.SerializedName
import com.pogoda.app.ru.utils.EMPTY

data class CurrentUnitsDto(
    @field:SerializedName("time")
    val time: String = String.EMPTY,

    @field:SerializedName("interval")
    val interval: String = String.EMPTY,

    @field:SerializedName("temperature_2m")
    val temperature2m: String = String.EMPTY,

    @field:SerializedName("apparent_temperature")
    val apparentTemperature: String = String.EMPTY,

    @field:SerializedName("is_day")
    val isDay: String = String.EMPTY,

    @field:SerializedName("weather_code")
    val weatherCode: String = String.EMPTY,
)