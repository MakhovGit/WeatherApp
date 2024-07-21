package com.pogoda.app.ru.data.retrofit.places

import com.pogoda.app.ru.data.dto.places.PlacesDto
import com.pogoda.app.ru.settings.PLACES_URL_QUERY
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitPlacesApi {
    @GET(PLACES_URL_QUERY)
    suspend fun getPlaces(
        @Query("name") name: String,
        @Query("count") count: Int,
        @Query("language") language: String,
        @Query("format") format: String,
    ): PlacesDto
}