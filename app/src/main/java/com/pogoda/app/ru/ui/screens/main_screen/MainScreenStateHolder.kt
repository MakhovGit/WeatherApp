package com.pogoda.app.ru.ui.screens.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainScreenStateHolder(
    private val reducer: MainScreenReducer
) {
    var screenState by mutableStateOf(MainScreenContract.State())
        private set

    fun update() {
        
    }
}