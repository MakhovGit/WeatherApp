package com.pogoda.app.ru.data.dto.weather

import com.google.gson.annotations.SerializedName

data class DailyWeatherDto(
    @field:SerializedName("time")
    val time: List<String> = listOf(),

    @field:SerializedName("weather_code")
    val weatherCode: List<Int> = listOf(),

    @field:SerializedName("temperature_2m_max")
    val temperature2mMax: List<Float> = listOf(),

    @field:SerializedName("temperature_2m_min")
    val temperature2mMin: List<Float> = listOf(),

    @field:SerializedName("apparent_temperature_max")
    val apparentTemperatureMax: List<Float> = listOf(),

    @field:SerializedName("apparent_temperature_min")
    val apparentTemperatureMin: List<Float> = listOf()
)