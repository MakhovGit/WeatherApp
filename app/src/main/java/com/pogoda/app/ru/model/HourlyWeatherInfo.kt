package com.pogoda.app.ru.model

import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.ZERO

data class HourlyWeatherInfo(
    val time: String = String.EMPTY,
    val icon: Int = Int.ZERO,
    val temp: String = String.EMPTY
)