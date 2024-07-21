package com.pogoda.app.ru.data.dto.weather

import com.google.gson.annotations.SerializedName
import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.ZERO

data class WeatherDto(
    @field:SerializedName("latitude")
    val latitude: Double = Double.ZERO,

    @field:SerializedName("longitude")
    val longitude: Double = Double.ZERO,

    @field:SerializedName("generationtime_ms")
    val generationTimeMs: Double = Double.ZERO,

    @field:SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int = Int.ZERO,

    @field:SerializedName("timezone")
    val timezone: String = String.EMPTY,

    @field:SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String = String.EMPTY,

    @field:SerializedName("elevation")
    val elevation: Double = Double.ZERO,

    @field:SerializedName("current_units")
    val currentUnits: CurrentUnitsDto = CurrentUnitsDto(),

    @field:SerializedName("current")
    val currentWeather: CurrentWeatherDto = CurrentWeatherDto(),

    @field:SerializedName("hourly_units")
    val hourlyUnits: HourlyUnitsDto = HourlyUnitsDto(),

    @field:SerializedName("hourly")
    val hourlyWeather: HourlyWeatherDto = HourlyWeatherDto(),

    @field:SerializedName("daily_units")
    val dailyUnits: DailyUnitsDto = DailyUnitsDto(),

    @field:SerializedName("daily")
    val dailyWeather: DailyWeatherDto = DailyWeatherDto()
)