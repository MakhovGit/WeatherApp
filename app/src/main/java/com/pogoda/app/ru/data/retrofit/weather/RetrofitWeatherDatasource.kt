package com.pogoda.app.ru.data.retrofit.weather

import com.google.gson.GsonBuilder
import com.pogoda.app.ru.data.dto.weather.WeatherDto
import com.pogoda.app.ru.settings.WEATHER_URL_BASE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitWeatherDatasource {
    private val retrofitWeatherApi = Retrofit
        .Builder()
        .baseUrl(WEATHER_URL_BASE)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(RetrofitWeatherApi::class.java)

    suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        currentWeather: String,
        hourlyWeather: String,
        dailyWeather: String,
        forecastDays: Int
    ): WeatherDto =
        retrofitWeatherApi.getWeather(
            latitude = latitude,
            longitude = longitude,
            currentWeather = currentWeather,
            hourlyWeather = hourlyWeather,
            dailyWeather = dailyWeather,
            forecastDays = forecastDays
        )
}