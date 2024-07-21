package com.pogoda.app.ru.data.facades.main_facade

import android.util.Log
import com.pogoda.app.ru.data.interactors.location_interactor.LocationInteractor
import com.pogoda.app.ru.data.interactors.location_interactor.LocationInteractorMessages
import com.pogoda.app.ru.data.interactors.network_interactor.NetworkInteractor
import com.pogoda.app.ru.data.interactors.network_interactor.NetworkInteractorMessages
import com.pogoda.app.ru.settings.MAIN_LOG_TAG
import com.pogoda.app.ru.utils.ONE
import com.pogoda.app.ru.utils.ZERO
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainFacade(
    private val networkInteractor: NetworkInteractor,
    private val locationInteractor: LocationInteractor
) {
    private val _outFlow: MutableSharedFlow<MainFacadeMessages> =
        MutableSharedFlow(replay = Int.ONE)
    val outFlow = _outFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "MainFacade error!: ${error.message}.")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    private var networkInteractorHandlerJob: Job? = null
    private var locationInteractorHandlerJob: Job? = null
    private var requestWeatherJob: Job? = null
    private var requestWeatherWithParamsJob: Job? = null
    private var requestPlacesJob: Job? = null
    private var erasePlacesJob: Job? = null
    private var eraseMessageJob: Job? = null
    private var allowLocating: Boolean = true

    init {
        setNetworkInteractorHandler()
        setLocationInteractorHandler()
    }

    private fun setNetworkInteractorHandler() {
        networkInteractorHandlerJob?.cancel()
        networkInteractorHandlerJob = mainScope.launch {
            networkInteractor.outFlow.collect { message ->
                when (message) {
                    is NetworkInteractorMessages.RequestWeather.Processing -> {}

                    is NetworkInteractorMessages.RequestWeather.Success -> {
                        Log.d(MAIN_LOG_TAG, "MainFacade: requestWeather() success!")
                        _outFlow.emit(
                            MainFacadeMessages.RequestWeather.Success(
                                weather = message.weather
                            )
                        )
                    }

                    is NetworkInteractorMessages.RequestWeather.Failure -> {
                        Log.e(MAIN_LOG_TAG, "MainFacade: requestWeather() failed!")
                        _outFlow.emit(
                            MainFacadeMessages.RequestWeather.Failure(
                                error = MainFacadeMessages.RequestWeatherErrors.RequestError()
                            )
                        )
                    }

                    is NetworkInteractorMessages.RequestPlaces.Processing -> {}

                    is NetworkInteractorMessages.RequestPlaces.Success -> {
                        Log.d(MAIN_LOG_TAG, "MainFacade: requestPlaces() success!")
                        if (message.places.places.size > Int.ZERO) {
                            _outFlow.emit(
                                MainFacadeMessages.RequestPlaces.Success(
                                    places = message.places
                                )
                            )
                        }
                    }

                    is NetworkInteractorMessages.RequestPlaces.Failure -> {
                        Log.e(MAIN_LOG_TAG, "MainFacade: requestPlaces() failed!")
                        _outFlow.emit(
                            MainFacadeMessages.RequestPlaces.Failure(
                                error = MainFacadeMessages.RequestPlacesErrors.RequestError()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setLocationInteractorHandler() {
        locationInteractorHandlerJob?.cancel()
        locationInteractorHandlerJob = mainScope.launch {
            locationInteractor.outFlow.collect { message ->
                when (message) {
                    is LocationInteractorMessages.RequestLocation.Processing -> {}

                    is LocationInteractorMessages.RequestLocation.Success -> {
                        locationInteractor.requestAddress(message.latitude, message.longitude)
                    }

                    is LocationInteractorMessages.RequestLocation.Failure -> {
                        Log.e(MAIN_LOG_TAG, "MainFacade: requestLocation() failed!")
                        _outFlow.emit(
                            MainFacadeMessages.RequestWeather.Failure(
                                error = MainFacadeMessages.RequestWeatherErrors.LocatingError()
                            )
                        )
                    }

                    is LocationInteractorMessages.RequestAddress.Processing -> {}

                    is LocationInteractorMessages.RequestAddress.Success -> {
                        networkInteractor.requestWeather(
                            latitude = message.latitude,
                            longitude = message.longitude,
                            place = message.address
                        )
                    }

                    is LocationInteractorMessages.RequestAddress.Failure -> {
                        networkInteractor.requestWeather(
                            latitude = message.latitude,
                            longitude = message.longitude,
                            place = UNKNOWN_PLACE
                        )
                    }
                }
            }
        }
    }

    fun requestPlaces(name: String) {
        requestPlacesJob?.cancel()
        requestPlacesJob = mainScope.launch {
            Log.d(MAIN_LOG_TAG, "MainFacade: requestPlaces() launched!")
            _outFlow.emit(MainFacadeMessages.RequestPlaces.Processing)
            networkInteractor.requestPlaces(name, PLACES_COUNT, LANGUAGE, FORMAT)
        }
    }

    fun erasePlaces() {
        erasePlacesJob?.cancel()
        erasePlacesJob = mainScope.launch {
            Log.d(MAIN_LOG_TAG, "MainFacade: erasePlaces() launched!")
            _outFlow.emit(MainFacadeMessages.RequestPlaces.ErasePlaces)
        }
    }

    fun requestWeather() {
        if (allowLocating) {
            requestWeatherJob?.cancel()
            requestWeatherJob = mainScope.launch {
                Log.d(MAIN_LOG_TAG, "MainFacade: requestWeather() launched!")
                _outFlow.emit(MainFacadeMessages.RequestWeather.Processing)
                locationInteractor.requestLocation()
            }
        }
    }

    fun requestWeatherWithParams(latitude: Double, longitude: Double, placeName: String) {
        allowLocating = false
        requestWeatherWithParamsJob?.cancel()
        requestWeatherWithParamsJob = mainScope.launch {
            Log.d(MAIN_LOG_TAG, "MainFacade: requestWeatherWithParams() launched!")
            _outFlow.emit(MainFacadeMessages.RequestWeather.Processing)
            networkInteractor.requestWeather(latitude, longitude, placeName)
        }
    }

    fun eraseMessage() {
        eraseMessageJob?.cancel()
        eraseMessageJob = mainScope.launch {
            Log.d(MAIN_LOG_TAG, "MainFacade: eraseMessage() launched!")
            _outFlow.emit(MainFacadeMessages.EraseMessage)
        }
    }

    companion object {
        private const val PLACES_COUNT = 10
        private const val LANGUAGE = "ru"
        private const val FORMAT = "json"
        private const val UNKNOWN_PLACE = "Неизвестное место"
    }
}