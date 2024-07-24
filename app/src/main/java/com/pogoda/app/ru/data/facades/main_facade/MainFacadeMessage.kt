package com.pogoda.app.ru.data.facades.main_facade

import com.pogoda.app.ru.model.places.PlacesInfo
import com.pogoda.app.ru.model.weather.WeatherInfo

sealed interface MainFacadeMessage {

    sealed class RequestWeatherError {
        data object GooglePlayServicesNotAvailable : RequestWeatherError()

        data object LocationPermissionsNotGranted : RequestWeatherError()

        data object LocationFailure : RequestWeatherError()

        data object LocationIsNull : RequestWeatherError()

        data object RequestFailure : RequestWeatherError()
    }

    sealed class RequestPlacesError {
        data object RequestFailure : RequestPlacesError()
    }

    data object EraseMessage : MainFacadeMessage

    sealed class RequestWeather : MainFacadeMessage {
        data object Processing : RequestWeather()
        data class Success(
            val weather: WeatherInfo
        ) : RequestWeather()

        data class Failure(
            val error: RequestWeatherError
        ) : RequestWeather()
    }

    sealed class RequestPlaces : MainFacadeMessage {
        data object Processing : RequestPlaces()
        data object ErasePlaces : RequestPlaces()
        data class Success(
            val places: PlacesInfo
        ) : RequestPlaces()

        data class Failure(
            val error: RequestPlacesError
        ) : RequestPlaces()
    }

}