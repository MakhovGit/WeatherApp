package com.pogoda.app.ru.data.mappers

import com.pogoda.app.ru.data.dto.places.PlaceDto
import com.pogoda.app.ru.data.dto.places.PlacesDto
import com.pogoda.app.ru.model.places.PlaceInfo
import com.pogoda.app.ru.model.places.PlacesInfo

class PlacesMapper {
    private fun map(placeDto: PlaceDto) =
        PlaceInfo(
            latitude = placeDto.latitude,
            longitude = placeDto.longitude,
            name = placeDto.name,
            country = placeDto.country,
            admin1 = placeDto.admin1,
            admin2 = placeDto.admin2,
            admin3 = placeDto.admin3,
            admin4 = placeDto.admin4
        )

    fun map(placesDto: PlacesDto) =
        PlacesInfo(
            places = placesDto.places.map { placeDto -> map(placeDto) }
        )
}