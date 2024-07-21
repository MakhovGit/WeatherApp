package com.pogoda.app.ru.data.dto.weather

import com.google.gson.annotations.SerializedName
import com.pogoda.app.ru.utils.EMPTY

data class DailyUnitsDto(
    @field:SerializedName("time")
    val time: String = String.EMPTY,

    @field:SerializedName("weather_code")
    val weatherCode: String = String.EMPTY,

    @field:SerializedName("temperature_2m_max")
    val temperature2mMax: String = String.EMPTY,

    @field:SerializedName("temperature_2m_min")
    val temperature2mMin: String = String.EMPTY,

    @field:SerializedName("apparent_temperature_max")
    val apparentTemperatureMax: String = String.EMPTY,

    @field:SerializedName("apparent_temperature_min")
    val apparentTemperatureMin: String = String.EMPTY
)