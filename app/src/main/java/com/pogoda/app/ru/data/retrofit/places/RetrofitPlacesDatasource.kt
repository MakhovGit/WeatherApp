package com.pogoda.app.ru.data.retrofit.places

import com.google.gson.GsonBuilder
import com.pogoda.app.ru.data.dto.places.PlacesDto
import com.pogoda.app.ru.settings.PLACES_URL_BASE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitPlacesDatasource {
    private val retrofitPlacesApi = Retrofit
        .Builder()
        .baseUrl(PLACES_URL_BASE)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(RetrofitPlacesApi::class.java)

    suspend fun getPlaces(
        name: String,
        count: Int,
        language: String,
        format: String
    ): PlacesDto = retrofitPlacesApi.getPlaces(name, count, language, format)
}