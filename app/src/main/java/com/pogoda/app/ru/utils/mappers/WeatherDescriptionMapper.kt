package com.pogoda.app.ru.utils.mappers

import android.app.Application
import com.pogoda.app.ru.R

class WeatherDescriptionMapper(
    private val app: Application
) {
    fun map(weatherCode: Int): String =
        when (weatherCode) {
            0 -> app.getString(R.string.weather_code_0)
            1 -> app.getString(R.string.weather_code_1)
            2 -> app.getString(R.string.weather_code_2)
            3 -> app.getString(R.string.weather_code_3)
            45 -> app.getString(R.string.weather_code_45)
            48 -> app.getString(R.string.weather_code_48)
            51 -> app.getString(R.string.weather_code_51)
            53 -> app.getString(R.string.weather_code_53)
            55 -> app.getString(R.string.weather_code_55)
            56 -> app.getString(R.string.weather_code_56)
            57 -> app.getString(R.string.weather_code_57)
            61 -> app.getString(R.string.weather_code_61)
            63 -> app.getString(R.string.weather_code_63)
            65 -> app.getString(R.string.weather_code_65)
            66 -> app.getString(R.string.weather_code_66)
            67 -> app.getString(R.string.weather_code_67)
            71 -> app.getString(R.string.weather_code_71)
            73 -> app.getString(R.string.weather_code_73)
            75 -> app.getString(R.string.weather_code_75)
            77 -> app.getString(R.string.weather_code_77)
            80 -> app.getString(R.string.weather_code_80)
            81 -> app.getString(R.string.weather_code_81)
            82 -> app.getString(R.string.weather_code_82)
            85 -> app.getString(R.string.weather_code_85)
            86 -> app.getString(R.string.weather_code_86)
            95 -> app.getString(R.string.weather_code_95)
            96 -> app.getString(R.string.weather_code_96)
            99 -> app.getString(R.string.weather_code_99)
            else -> throw RuntimeException(ERROR_MESSAGE)
        }

    companion object {
        private const val ERROR_MESSAGE = "Неизвестный погодный код!"
    }
}