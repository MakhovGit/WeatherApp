package com.pogoda.app.ru.data.repository

import com.pogoda.app.ru.data.dto.places.PlacesDto
import com.pogoda.app.ru.data.dto.weather.WeatherDto
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        currentWeather: String,
        hourlyWeather: String,
        dailyWeather: String,
        forecastDays: Int
    ): Flow<WeatherDto>

    suspend fun getPlaces(
        name: String,
        count: Int,
        language: String,
        format: String
    ): Flow<PlacesDto>
}