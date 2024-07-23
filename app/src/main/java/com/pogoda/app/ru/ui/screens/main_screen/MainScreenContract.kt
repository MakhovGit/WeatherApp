package com.pogoda.app.ru.ui.screens.main_screen

import com.pogoda.app.ru.data.facades.main_facade.MainFacadeMessage
import com.pogoda.app.ru.model.places.PlacesInfo
import com.pogoda.app.ru.model.weather.WeatherInfo
import com.pogoda.app.ru.ui.base.ViewEvent
import com.pogoda.app.ru.ui.base.ViewState
import com.pogoda.app.ru.utils.EMPTY

class MainScreenContract {

    data class State(
        val isWeatherLoading: Boolean = false,
        val isWeatherLoaded: Boolean = false,
        val weatherError: MainFacadeMessage.RequestWeatherError? = null,
        val weatherData: WeatherInfo = WeatherInfo(),
        val isPlacesLoading: Boolean = false,
        val isPlacesLoaded: Boolean = false,
        val placesError: MainFacadeMessage.RequestPlacesError? = null,
        val placesData: PlacesInfo = PlacesInfo(),
        val messageForUser: String = String.EMPTY
    ) : ViewState

    sealed class Event : ViewEvent {
        data object RequestWeather : Event()

        data class RequestWeatherWithParams(
            val placeName: String,
            val latitude: Double,
            val longitude: Double
        ) : Event()

        data class SearchPlaces(
            val query: String
        ) : Event()

        data object ErasePlaces : Event()

        data object EraseMessage : Event()
    }
}