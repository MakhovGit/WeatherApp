package com.pogoda.app.ru.data.facades.main_facade

import com.pogoda.app.ru.model.places.PlacesInfo
import com.pogoda.app.ru.model.weather.WeatherInfo

sealed interface MainFacadeMessages {

    sealed class RequestWeatherErrors {
        data class LocatingError(
            val message: String = "Ошибка! Невозможно определить текущие координаты!"
        ) : RequestWeatherErrors()

        data class RequestError(
            val message: String = "Ошибка! Невозможно запросить прогноз!"
        ) : RequestWeatherErrors()
    }

    sealed class RequestPlacesErrors {
        data class RequestError(
            val message: String = "Ошибка! Невозможно запросить список мест!"
        ) : RequestPlacesErrors()
    }

    data object EraseMessage : MainFacadeMessages

    sealed class RequestWeather : MainFacadeMessages {
        data object Processing : RequestWeather()
        data class Success(
            val weather: WeatherInfo
        ) : RequestWeather()

        data class Failure(
            val error: RequestWeatherErrors
        ) : RequestWeather()
    }

    sealed class RequestPlaces : MainFacadeMessages {
        data object Processing : RequestPlaces()
        data object ErasePlaces : RequestPlaces()
        data class Success(
            val places: PlacesInfo
        ) : RequestPlaces()

        data class Failure(
            val error: RequestPlacesErrors
        ) : RequestPlaces()
    }

}