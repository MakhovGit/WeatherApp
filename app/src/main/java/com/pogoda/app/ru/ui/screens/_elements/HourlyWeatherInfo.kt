package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pogoda.app.ru.R
import com.pogoda.app.ru.ui.theme.InterfaceBlockColor

@Composable
fun HourlyWeatherInfo() {
    Surface(
        color = InterfaceBlockColor,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_rounded_corner)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.common_padding))
    ) {
        LazyRow(
            contentPadding = PaddingValues(
                dimensionResource(id = R.dimen.hourly_weather_info_lazy_row_padding)
            ),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_20)),
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(10) {
                item {
                    HourlyWeatherItem(time = "10:20", icon = R.drawable.weather_02, temp = "8")
                }
            }
        }
    }
}