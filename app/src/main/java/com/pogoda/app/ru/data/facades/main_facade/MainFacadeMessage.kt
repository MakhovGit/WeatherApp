package com.pogoda.app.ru.data.facades.main_facade

import com.pogoda.app.ru.model.places.PlacesInfo
import com.pogoda.app.ru.model.weather.WeatherInfo

sealed interface MainFacadeMessage {

    sealed class RequestWeatherError : MainFacadeError {
        data class GooglePlayServicesNotAvailable(
            override val message: String = "Ошибка! Google Play Services недоступны на устройстве!"
        ) : RequestWeatherError()

        data class LocationPermissionsNotGranted(
            override val message: String = "Ошибка! Нет разрешений на определение местоположения!"
        ) : RequestWeatherError()

        data class LocationFailure(
            override val message: String = "Ошибка! Невозможно определить текущие координаты!"
        ) : RequestWeatherError()

        data class LocationIsNull(
            override val message: String = "Ошибка! Текущие координаты неверны!"
        ) : RequestWeatherError()

        data class RequestFailure(
            override val message: String = "Ошибка! Невозможно запросить прогноз!"
        ) : RequestWeatherError()
    }

    sealed class RequestPlacesError : MainFacadeError {
        data class RequestFailure(
            override val message: String = "Ошибка! Невозможно запросить список мест!"
        ) : RequestPlacesError()
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