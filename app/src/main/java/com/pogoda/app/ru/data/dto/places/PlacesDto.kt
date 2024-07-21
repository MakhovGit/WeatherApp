package com.pogoda.app.ru.data.dto.places

import com.google.gson.annotations.SerializedName
import com.pogoda.app.ru.utils.ZERO

data class PlacesDto(
    @field:SerializedName("results")
    val places: List<PlaceDto> = listOf(),

    @field:SerializedName("generationtime_ms")
    val generationTimeMs: Double = Double.ZERO
)