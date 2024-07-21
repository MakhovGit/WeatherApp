package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pogoda.app.ru.R
import com.pogoda.app.ru.model.weather.DailyWeatherInfo

@Composable
fun DailyWeatherInfo(
    dailyWeatherInfo: List<DailyWeatherInfo>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_20)),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.common_padding))
    ) {
        dailyWeatherInfo.forEachIndexed { index, dailyWeather ->
            item {
                DailyWeatherItem(dailyWeather, index)
            }
        }
    }
}