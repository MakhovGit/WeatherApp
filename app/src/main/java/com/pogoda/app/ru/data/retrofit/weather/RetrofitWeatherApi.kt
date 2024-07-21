package com.pogoda.app.ru.data.retrofit.weather

import com.pogoda.app.ru.data.dto.weather.WeatherDto
import com.pogoda.app.ru.settings.WEATHER_URL_QUERY
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitWeatherApi {
    @GET(WEATHER_URL_QUERY)
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") currentWeather: String,
        @Query("hourly") hourlyWeather: String,
        @Query("daily") dailyWeather: String,
        @Query("forecast_days") forecastDays: Int
    ): WeatherDto
}