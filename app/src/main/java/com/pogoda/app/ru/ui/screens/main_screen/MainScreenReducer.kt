package com.pogoda.app.ru.ui.screens.main_screen

import android.util.Log
import com.pogoda.app.ru.data.facades.main_facade.MainFacadeMessage
import com.pogoda.app.ru.model.places.PlacesInfo
import com.pogoda.app.ru.settings.MAIN_LOG_TAG
import com.pogoda.app.ru.utils.EMPTY

class MainScreenReducer {

    private fun onRequestWeather(
        currentState: MainScreenContract.State,
        result: MainFacadeMessage.RequestWeather
    ): MainScreenContract.State {
        return when (result) {
            is MainFacadeMessage.RequestWeather.Processing -> {
                Log.d(MAIN_LOG_TAG, "Reducer: RequestWeather.Processing!")
                currentState.copy(
                    isWeatherLoading = true,
                    isWeatherLoaded = false,
                    weatherError = null
                )
            }

            is MainFacadeMessage.RequestWeather.Success -> {
                Log.d(MAIN_LOG_TAG, "Reducer: RequestWeather.Success!")
                currentState.copy(
                    isWeatherLoading = false,
                    isWeatherLoaded = true,
                    weatherData = result.weather,
                    weatherError = null
                )
            }

            is MainFacadeMessage.RequestWeather.Failure -> {
                Log.d(MAIN_LOG_TAG, "Reducer: RequestWeather.Failure!")
                currentState.copy(
                    isWeatherLoading = false,
                    isWeatherLoaded = false,
                    weatherError = result.error,
                    messageForUser = result.error.message
                )
            }
        }
    }

    private fun onRequestPlaces(
        currentState: MainScreenContract.State,
        result: MainFacadeMessage.RequestPlaces
    ): MainScreenContract.State {
        return when (result) {
            is MainFacadeMessage.RequestPlaces.Processing -> {
                Log.d(MAIN_LOG_TAG, "Reducer: RequestPlaces.Processing!")
                currentState.copy(
                    isPlacesLoading = true,
                    isPlacesLoaded = false,
                    placesError = null
                )
            }

            is MainFacadeMessage.RequestPlaces.Success -> {
                Log.d(MAIN_LOG_TAG, "Reducer: RequestPlaces.Success!")
                currentState.copy(
                    isPlacesLoading = false,
                    isPlacesLoaded = true,
                    placesData = result.places,
                    placesError = null
                )
            }

            is MainFacadeMessage.RequestPlaces.Failure -> {
                Log.d(MAIN_LOG_TAG, "Reducer: RequestPlaces.Failure!")
                currentState.copy(
                    isPlacesLoading = false,
                    isPlacesLoaded = false,
                    placesError = result.error,
                    messageForUser = result.error.message
                )
            }

            is MainFacadeMessage.RequestPlaces.ErasePlaces -> {
                Log.d(MAIN_LOG_TAG, "Reducer: RequestPlaces.ErasePlaces!")
                currentState.copy(
                    isPlacesLoading = false,
                    isPlacesLoaded = false,
                    placesData = PlacesInfo(),
                    placesError = null,
                )
            }
        }
    }

    private fun onEraseMessage(currentState: MainScreenContract.State): MainScreenContract.State {
        return currentState.copy(
            messageForUser = String.EMPTY
        )
    }

    fun reduce(
        currentState: MainScreenContract.State,
        result: MainFacadeMessage
    ): MainScreenContract.State {
        return when (result) {
            is MainFacadeMessage.RequestWeather -> onRequestWeather(currentState, result)
            is MainFacadeMessage.RequestPlaces -> onRequestPlaces(currentState, result)
            is MainFacadeMessage.EraseMessage -> onEraseMessage(currentState)
            else -> throw RuntimeException(getErrorMessage(result))
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "Invalid event type! Don't know, how reduce this: "
        private fun getErrorMessage(message: MainFacadeMessage) = "$ERROR_MESSAGE $message"
    }
}