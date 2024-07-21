package com.pogoda.app.ru.ui.screens.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.pogoda.app.ru.data.facades.main_facade.MainFacade
import com.pogoda.app.ru.settings.MAIN_LOG_TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val mainFacade: MainFacade,
    private val stateHolder: MainScreenStateHolder
) : ViewModel() {
    val screenState: MainScreenContract.State
        get() = stateHolder.screenState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "MainScreenViewModel error!: ${error.message}.")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    private var mainFacadeHandlerJob: Job? = null

    init {
        setMainFacadeHandler()
    }

    private fun setMainFacadeHandler() {
        mainFacadeHandlerJob?.cancel()
        mainFacadeHandlerJob = mainScope.launch {
            mainFacade.outFlow.collect { message ->
                launch(context = Dispatchers.Main) {
                    stateHolder.update(message)
                }
            }
        }
    }

    fun handleEvent(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.RequestWeather -> {
                Log.d(MAIN_LOG_TAG, "ViewModel: RequestWeather event handled!")
                mainFacade.requestWeather()
            }

            is MainScreenContract.Event.RequestWeatherWithParams -> {
                Log.d(MAIN_LOG_TAG, "ViewModel: RequestWeatherWithParams event handled!")
                mainFacade.requestWeatherWithParams(
                    latitude = event.latitude,
                    longitude = event.longitude,
                    placeName = event.placeName
                )
            }

            is MainScreenContract.Event.SearchPlaces -> {
                Log.d(MAIN_LOG_TAG, "ViewModel: SearchPlaces event handled!")
                mainFacade.requestPlaces(name = event.query)
            }

            is MainScreenContract.Event.ErasePlaces -> {
                Log.d(MAIN_LOG_TAG, "ViewModel: ErasePlaces event handled!")
                mainFacade.erasePlaces()
            }

            is MainScreenContract.Event.EraseMessage -> {
                Log.d(MAIN_LOG_TAG, "ViewModel: EraseMessage event handled!")
                mainFacade.eraseMessage()
            }
        }
    }

}