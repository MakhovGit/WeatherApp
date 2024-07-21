package com.pogoda.app.ru.model.places

import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.ZERO

data class PlaceInfo(
    val latitude: Double = Double.ZERO,
    val longitude: Double = Double.ZERO,
    val name: String = String.EMPTY,
    val country: String = String.EMPTY,
    val admin1: String = String.EMPTY,
    val admin2: String = String.EMPTY,
    val admin3: String = String.EMPTY,
    val admin4: String = String.EMPTY
)
