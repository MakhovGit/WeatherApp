package com.pogoda.app.ru.data.dto.places

import com.google.gson.annotations.SerializedName
import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.ZERO

data class PlaceDto(
    @field:SerializedName("id")
    val id: Int = Int.ZERO,

    @field:SerializedName("name")
    val name: String = String.EMPTY,

    @field:SerializedName("latitude")
    val latitude: Double = Double.ZERO,

    @field:SerializedName("longitude")
    val longitude: Double = Double.ZERO,

    @field:SerializedName("elevation")
    val elevation: Double = Double.ZERO,

    @field:SerializedName("feature_code")
    val featureCode: String = String.EMPTY,

    @field:SerializedName("country_code")
    val countryCode: String = String.EMPTY,

    @field:SerializedName("admin1_id")
    val admin1Id: Int = Int.ZERO,

    @field:SerializedName("admin2_id")
    val admin2Id: Int = Int.ZERO,

    @field:SerializedName("admin3_id")
    val admin3Id: Int = Int.ZERO,

    @field:SerializedName("admin4_id")
    val admin4Id: Int = Int.ZERO,

    @field:SerializedName("timezone")
    val timezone: String = String.EMPTY,

    @field:SerializedName("population")
    val population: Int = Int.ZERO,

    @field:SerializedName("postcodes")
    val postCodes: List<String> = listOf(),

    @field:SerializedName("country_id")
    val countryId: Int = Int.ZERO,

    @field:SerializedName("country")
    val country: String = String.EMPTY,

    @field:SerializedName("admin1")
    val admin1: String = String.EMPTY,

    @field:SerializedName("admin2")
    val admin2: String = String.EMPTY,

    @field:SerializedName("admin3")
    val admin3: String = String.EMPTY,

    @field:SerializedName("admin4")
    val admin4: String = String.EMPTY
)