package com.pogoda.app.ru.data.interactors.network_interactor

import android.util.Log
import com.pogoda.app.ru.data.dto.places.PlacesDto
import com.pogoda.app.ru.data.dto.weather.WeatherDto
import com.pogoda.app.ru.data.mappers.PlacesMapper
import com.pogoda.app.ru.data.mappers.WeatherMapper
import com.pogoda.app.ru.data.repository.NetworkRepository
import com.pogoda.app.ru.settings.CURRENT_WEATHER
import com.pogoda.app.ru.settings.DAILY_WEATHER
import com.pogoda.app.ru.settings.FORECAST_DAYS
import com.pogoda.app.ru.settings.HOURLY_WEATHER
import com.pogoda.app.ru.settings.MAIN_LOG_TAG
import com.pogoda.app.ru.utils.ONE
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch

class NetworkInteractor(
    private val repository: NetworkRepository,
    private val weatherMapper: WeatherMapper,
    private val placesMapper: PlacesMapper
) {
    private val _outFlow: MutableSharedFlow<NetworkInteractorMessages> =
        MutableSharedFlow(replay = Int.ONE)
    val outFlow = _outFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "Network interactor error!: ${error.message.toString()}")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
    private var requestWeatherJob: Job? = null
    private var requestPlacesJob: Job? = null

    private suspend fun onRequestWeatherError(error: Throwable) {
        _outFlow.emit(
            NetworkInteractorMessages.RequestWeather.Failure(
                error = NetworkInteractorMessages.RequestWeatherError.RequestError
            )
        )
        Log.e(MAIN_LOG_TAG, "NetworkInteractor: requestWeather() failed! $error")
    }

    private suspend fun onRequestWeatherSuccess(place: String, weatherDto: WeatherDto) {
        Log.d(MAIN_LOG_TAG, "NetworkInteractor: requestWeather() success!")
        val weatherInfo = weatherMapper.map(place, weatherDto)
        _outFlow.emit(
            NetworkInteractorMessages.RequestWeather.Success(
                weather = weatherInfo
            )
        )
    }

    private suspend fun onRequestPlacesError(error: Throwable) {
        _outFlow.emit(
            NetworkInteractorMessages.RequestPlaces.Failure(
                error = NetworkInteractorMessages.RequestPlacesError.RequestError
            )
        )
        Log.e(MAIN_LOG_TAG, "NetworkInteractor: requestPlaces() failed! $error")
    }

    private suspend fun onRequestPlacesSuccess(placesDto: PlacesDto) {
        Log.d(MAIN_LOG_TAG, "NetworkInteractor: requestPlaces() success!")
        val placesInfo = placesMapper.map(placesDto)
        _outFlow.emit(
            NetworkInteractorMessages.RequestPlaces.Success(
                places = placesInfo
            )
        )
    }

    fun requestWeather(latitude: Double, longitude: Double, place: String) {
        requestWeatherJob?.cancel()
        requestWeatherJob = mainScope.launch {
            _outFlow.emit(NetworkInteractorMessages.RequestWeather.Processing)
            Log.d(MAIN_LOG_TAG, "NetworkInteractor: requestWeather() launched!")
            repository.getWeather(
                latitude = latitude,
                longitude = longitude,
                currentWeather = CURRENT_WEATHER,
                hourlyWeather = HOURLY_WEATHER,
                dailyWeather = DAILY_WEATHER,
                forecastDays = FORECAST_DAYS
            )
                .retry(retries = REQUEST_WEATHER_MAX_RETRIES)
                .catch { error ->
                    onRequestWeatherError(error)
                }
                .collect { weatherDto ->
                    onRequestWeatherSuccess(place, weatherDto)
                }
        }
    }

    fun requestPlaces(placeName: String, count: Int, language: String, format: String) {
        requestPlacesJob?.cancel()
        requestPlacesJob = mainScope.launch {
            _outFlow.emit(NetworkInteractorMessages.RequestPlaces.Processing)
            Log.d(MAIN_LOG_TAG, "NetworkInteractor: requestPlaces() launched!")
            repository.getPlaces(placeName, count, language, format)
                .retry(retries = REQUEST_PLACES_MAX_RETRIES)
                .catch { error ->
                    onRequestPlacesError(error)
                }
                .collect { placesDto ->
                    onRequestPlacesSuccess(placesDto)
                }
        }
    }

    companion object {
        private const val REQUEST_WEATHER_MAX_RETRIES = 5L
        private const val REQUEST_PLACES_MAX_RETRIES = 5L
    }
}