package com.pogoda.app.ru.model.weather

import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.ZERO

data class HourlyWeatherInfo(
    val time: String = String.EMPTY,
    val icon: Int = Int.ZERO,
    val temperature: String = String.EMPTY,
    val precipitationProbability: Int = Int.ZERO,
    val probabilityUnit: String = String.EMPTY
)