package com.pogoda.app.ru.ui.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pogoda.app.ru.R
import com.pogoda.app.ru.ui.screens._elements.DailyWeatherInfo
import com.pogoda.app.ru.ui.screens._elements.HourlyWeatherInfo
import com.pogoda.app.ru.ui.screens._elements.PrecipitationInfo
import com.pogoda.app.ru.ui.screens._elements.Search
import com.pogoda.app.ru.ui.theme.MainBackgroundColor

@Composable
fun MainScreen() {
    var isTodayButtonPressed by remember { mutableStateOf(true) }
    var isTomorrowButtonPressed by remember { mutableStateOf(false) }
    var is10daysButtonPressed by remember { mutableStateOf(false) }
    Scaffold(
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Surface(
                    color = MainBackgroundColor,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionResource(id = R.dimen.spacer_20)
                        ),
                        modifier = Modifier.padding(
                            bottom =
                            dimensionResource(id = R.dimen.common_padding)
                        )
                    ) {
                        Search(
                            isTodayButtonPressed = isTodayButtonPressed,
                            isTomorrowButtonPressed = isTomorrowButtonPressed,
                            is10daysButtonPressed = is10daysButtonPressed,
                            onTodayButtonPressed = {
                                isTodayButtonPressed = true
                                isTomorrowButtonPressed = false
                                is10daysButtonPressed = false
                            },
                            onTomorrowButtonPressed = {
                                isTodayButtonPressed = false
                                isTomorrowButtonPressed = true
                                is10daysButtonPressed = false
                            },
                            on10daysButtonPressed = {
                                isTodayButtonPressed = false
                                isTomorrowButtonPressed = false
                                is10daysButtonPressed = true
                            }
                        )
                        if (isTodayButtonPressed || isTomorrowButtonPressed) {
                            HourlyWeatherInfo()
                            PrecipitationInfo()
                        } else {
                            DailyWeatherInfo()
                        }
                    }
                }
            }
        }
    )
}