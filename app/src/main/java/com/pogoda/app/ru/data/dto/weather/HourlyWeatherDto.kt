package com.pogoda.app.ru.data.dto.weather

import com.google.gson.annotations.SerializedName

data class HourlyWeatherDto(
    @field:SerializedName("time")
    val time: List<String> = listOf(),

    @field:SerializedName("temperature_2m")
    val temperature2m: List<Float> = listOf(),

    @field:SerializedName("precipitation_probability")
    val precipitationProbability: List<Int> = listOf(),

    @field:SerializedName("weather_code")
    val weatherCode: List<Int> = listOf(),
)