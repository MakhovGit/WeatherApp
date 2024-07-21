package com.pogoda.app.ru.data.repository

import com.pogoda.app.ru.data.retrofit.places.RetrofitPlacesDatasource
import com.pogoda.app.ru.data.retrofit.weather.RetrofitWeatherDatasource
import kotlinx.coroutines.flow.flow

class NetworkRepositoryImpl(
    private val retrofitWeatherDatasource: RetrofitWeatherDatasource,
    private val retrofitPlacesDatasource: RetrofitPlacesDatasource
) : NetworkRepository {
    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        currentWeather: String,
        hourlyWeather: String,
        dailyWeather: String,
        forecastDays: Int
    ) = flow {
        emit(
            retrofitWeatherDatasource.getWeather(
                latitude = latitude,
                longitude = longitude,
                currentWeather = currentWeather,
                hourlyWeather = hourlyWeather,
                dailyWeather = dailyWeather,
                forecastDays = forecastDays
            )
        )
    }

    override suspend fun getPlaces(name: String, count: Int, language: String, format: String) =
        flow {
            emit(retrofitPlacesDatasource.getPlaces(name, count, language, format))
        }
}