package com.pogoda.app.ru.model.weather

data class WeatherInfo(
    val currentWeatherInfo: CurrentWeatherInfo = CurrentWeatherInfo(),
    val todayHourlyWeatherInfo: List<HourlyWeatherInfo> = listOf(),
    val tomorrowHourlyWeatherInfo: List<HourlyWeatherInfo> = listOf(),
    val dailyWeatherInfo: List<DailyWeatherInfo> = listOf()
)