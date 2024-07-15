package com.pogoda.app.ru.model.weather

import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.ZERO

data class DailyWeatherInfo(
    val date: String = String.EMPTY,
    val description: String = String.EMPTY,
    val tempReal: String = String.EMPTY,
    val tempFeel: String = String.EMPTY,
    val icon: Int = Int.ZERO
)