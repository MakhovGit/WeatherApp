package com.pogoda.app.ru.model.weather

import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.ZERO

data class PrecipitationInfo(
    val time: String = String.EMPTY,
    val probability: Int = Int.ZERO
)