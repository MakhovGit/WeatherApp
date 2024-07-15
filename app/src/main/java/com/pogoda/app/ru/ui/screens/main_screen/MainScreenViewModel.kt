package com.pogoda.app.ru.ui.screens.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.pogoda.app.ru.settings.MAIN_LOG_TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val stateHolder: MainScreenStateHolder
) : ViewModel() {
    val screenState: MainScreenContract.State
        get() = stateHolder.screenState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "${this::class.simpleName.toString()} error!: ${error.message}.")
        error.printStackTrace()
    }
    val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    private var networkInteractorHandlerJob: Job? = null
    private var handleEventJob: Job? = null

    init {
        setNetworkInteractorHandler()
    }

    private fun setNetworkInteractorHandler() {
        networkInteractorHandlerJob?.cancel()
        networkInteractorHandlerJob = mainScope.launch {

        }
    }

    fun handleEvent(event: MainScreenContract.Event) {
        handleEventJob?.cancel()
        handleEventJob = mainScope.launch {
            when (event) {
                is MainScreenContract.Event.RequestWeather -> {

                }
            }
        }
    }

}