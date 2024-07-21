package com.pogoda.app.ru.utils.mappers

import com.pogoda.app.ru.R

class WeatherIconMapper {
    fun map(weatherCode: Int): Int =
        when (weatherCode) {
            0 -> R.drawable.weather_02
            1, 2 -> R.drawable.weather_08
            3 -> R.drawable.weather_03
            45, 48 -> R.drawable.weather_05
            51, 53, 55, 56, 57 -> R.drawable.weather_01
            61, 63, 65 -> R.drawable.weather_06
            66, 67 -> R.drawable.weather_04
            71, 73, 75, 77 -> R.drawable.weather_11
            80, 81, 82 -> R.drawable.weather_09
            85, 86 -> R.drawable.weather_04
            95, 96, 99 -> R.drawable.weather_07
            else -> throw RuntimeException(ERROR_MESSAGE)
        }

    companion object {
        private const val ERROR_MESSAGE = "Неизвестный погодный код!"
    }
}