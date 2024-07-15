package com.pogoda.app.ru.ui.screens.main_screen

import com.pogoda.app.ru.model.weather.DailyWeatherInfo
import com.pogoda.app.ru.model.weather.HourlyWeatherInfo
import com.pogoda.app.ru.model.weather.NowWeatherInfo
import com.pogoda.app.ru.model.weather.PrecipitationInfo
import com.pogoda.app.ru.ui.base.ViewEvent
import com.pogoda.app.ru.ui.base.ViewState

class MainScreenContract {

    data class State(
        val isLoading: Boolean = true,
        val error: Throwable? = null,
        val nowWeather: NowWeatherInfo = NowWeatherInfo(),
        val hourlyWeather: List<HourlyWeatherInfo> = listOf(),
        val precipitation: List<PrecipitationInfo> = listOf(),
        val dailyWeather: List<DailyWeatherInfo> = listOf()
    ) : ViewState

    sealed class Event : ViewEvent {
        data class RequestWeather(
            val place: String
        ) : Event()
        
    }
}