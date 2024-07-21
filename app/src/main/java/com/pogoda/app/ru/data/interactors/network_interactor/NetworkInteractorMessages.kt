package com.pogoda.app.ru.data.interactors.network_interactor

import com.pogoda.app.ru.model.places.PlacesInfo
import com.pogoda.app.ru.model.weather.WeatherInfo

sealed interface NetworkInteractorMessages {
    sealed class RequestWeatherError {
        data object RequestError : RequestWeatherError()
    }

    sealed class RequestPlacesError {
        data object RequestError : RequestPlacesError()
    }

    sealed class RequestWeather : NetworkInteractorMessages {
        data object Processing : RequestWeather()
        data class Success(
            val weather: WeatherInfo
        ) : RequestWeather()

        data class Failure(
            val error: RequestWeatherError
        ) : RequestWeather()
    }

    sealed class RequestPlaces : NetworkInteractorMessages {
        data object Processing : RequestPlaces()
        data class Success(
            val places: PlacesInfo
        ) : RequestPlaces()

        data class Failure(
            val error: RequestPlacesError
        ) : RequestPlaces()
    }
}