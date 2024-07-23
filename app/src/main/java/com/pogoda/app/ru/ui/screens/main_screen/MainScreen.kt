package com.pogoda.app.ru.ui.screens.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.pogoda.app.ru.R
import com.pogoda.app.ru.ui.screens._elements.CurrentWeatherInfo
import com.pogoda.app.ru.ui.screens._elements.DailyWeatherInfo
import com.pogoda.app.ru.ui.screens._elements.HourlyWeatherInfo
import com.pogoda.app.ru.ui.screens._elements.PrecipitationInfo
import com.pogoda.app.ru.ui.screens._elements.Search
import com.pogoda.app.ru.ui.screens._elements.SimpleButton
import com.pogoda.app.ru.ui.theme.AltBackgroundColor
import com.pogoda.app.ru.ui.theme.MainBackgroundColor
import com.pogoda.app.ru.utils.MAX_WEIGHT
import com.pogoda.app.ru.utils.VerticalSpacer
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val viewModel: MainScreenViewModel = koinViewModel()
    val state = viewModel.screenState
    val focusManager = LocalFocusManager.current
    var isWeatherRequested by remember { mutableStateOf(false) }
    var isWeatherLoaded by remember { mutableStateOf(false) }
    var isTodayButtonPressed by remember { mutableStateOf(true) }
    var isTomorrowButtonPressed by remember { mutableStateOf(false) }
    var is10daysButtonPressed by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    if (isWeatherRequested.not()) {
        viewModel.handleEvent(MainScreenContract.Event.RequestWeather)
        isWeatherRequested = true
    }
    if (state.isWeatherLoaded) {
        isWeatherLoaded = true
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                LaunchedEffect(key1 = state.messageForUser) {
                    if (state.messageForUser.isNotEmpty()) {
                        keyboardController?.hide()
                        scope.launch {
                            snackbarHostState.showSnackbar(state.messageForUser)
                        }
                        viewModel.handleEvent(MainScreenContract.Event.EraseMessage)
                    }
                }
                Surface(
                    color = MainBackgroundColor,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource()
                        ) {
                            focusManager.clearFocus()
                        }
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionResource(id = R.dimen.spacer_20)
                        ),
                        modifier = Modifier.padding(
                            bottom = dimensionResource(id = R.dimen.common_padding)
                        )
                    ) {
                        Surface(
                            color = AltBackgroundColor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = dimensionResource(id = R.dimen.main_screen_search_elevation))
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.common_padding))
                            ) {
                                Search(
                                    places = state.placesData,
                                    isPlacesLoaded = state.isPlacesLoaded,
                                    requestPlaces = { placeName ->
                                        viewModel.handleEvent(
                                            MainScreenContract.Event.SearchPlaces(
                                                placeName
                                            )
                                        )
                                    },
                                    requestWeather = { latitude, longitude, placeName ->
                                        viewModel.handleEvent(
                                            MainScreenContract.Event.RequestWeatherWithParams(
                                                latitude = latitude,
                                                longitude = longitude,
                                                placeName = placeName
                                            )
                                        )
                                    },
                                    erasePlaces = {
                                        viewModel.handleEvent(MainScreenContract.Event.ErasePlaces)
                                    }
                                )
                                if (isWeatherLoaded) {
                                    CurrentWeatherInfo(state.weatherData.currentWeatherInfo)
                                    VerticalSpacer(dimensionResource(id = R.dimen.spacer_10))
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            dimensionResource(id = R.dimen.spacer_10)
                                        )
                                    ) {
                                        SimpleButton(
                                            modifier = Modifier.weight(Float.MAX_WEIGHT),
                                            text = stringResource(id = R.string.button_today),
                                            fontSize = dimensionResource(id = R.dimen.main_screen_button_font_size).value.sp,
                                            isPressed = isTodayButtonPressed
                                        ) {
                                            isTodayButtonPressed = true
                                            isTomorrowButtonPressed = false
                                            is10daysButtonPressed = false
                                        }
                                        SimpleButton(
                                            modifier = Modifier.weight(Float.MAX_WEIGHT),
                                            text = stringResource(id = R.string.button_tomorrow),
                                            fontSize = dimensionResource(id = R.dimen.main_screen_button_font_size).value.sp,
                                            isPressed = isTomorrowButtonPressed
                                        ) {
                                            isTodayButtonPressed = false
                                            isTomorrowButtonPressed = true
                                            is10daysButtonPressed = false
                                        }
                                        SimpleButton(
                                            modifier = Modifier.weight(Float.MAX_WEIGHT),
                                            text = stringResource(id = R.string.button_10days),
                                            fontSize = dimensionResource(id = R.dimen.main_screen_button_font_size).value.sp,
                                            isPressed = is10daysButtonPressed
                                        ) {
                                            isTodayButtonPressed = false
                                            isTomorrowButtonPressed = false
                                            is10daysButtonPressed = true
                                        }
                                    }
                                    VerticalSpacer(dimensionResource(id = R.dimen.spacer_20))
                                }
                            }
                        }
                        if (isWeatherLoaded) {
                            if (isTodayButtonPressed) {
                                HourlyWeatherInfo(
                                    hourlyWeather = viewModel.screenState.weatherData.todayHourlyWeatherInfo,
                                    isToday = true
                                )
                                PrecipitationInfo(viewModel.screenState.weatherData.todayHourlyWeatherInfo)
                            }
                            if (isTomorrowButtonPressed) {
                                HourlyWeatherInfo(viewModel.screenState.weatherData.tomorrowHourlyWeatherInfo)
                                PrecipitationInfo(viewModel.screenState.weatherData.tomorrowHourlyWeatherInfo)
                            }
                            if (is10daysButtonPressed) {
                                DailyWeatherInfo(viewModel.screenState.weatherData.dailyWeatherInfo)
                            }
                        }
                    }
                }
            }
        }
    )
}