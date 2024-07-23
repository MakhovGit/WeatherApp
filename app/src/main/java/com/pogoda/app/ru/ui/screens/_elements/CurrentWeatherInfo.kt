package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pogoda.app.ru.R
import com.pogoda.app.ru.model.weather.CurrentWeatherInfo
import com.pogoda.app.ru.utils.MAX_WEIGHT
import com.pogoda.app.ru.utils.SPACE
import com.pogoda.app.ru.utils.VerticalSpacer

@Composable
fun CurrentWeatherInfo(
    currentWeather: CurrentWeatherInfo
) {
    Box {
        Column {
            Text(
                textAlign = TextAlign.Start,
                text = currentWeather.place,
                fontSize = dimensionResource(id = R.dimen.current_weather_info_place_font_size).value.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            VerticalSpacer(dimensionResource(id = R.dimen.spacer_10))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_10)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = currentWeather.temperature,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.current_weather_info_temp_font_size).value.sp,
                    modifier = Modifier.alignByBaseline()
                )
                Text(
                    text = stringResource(id = R.string.current_weather_info_apparent) + String.SPACE +
                            currentWeather.apparentTemperature,
                    textAlign = TextAlign.Center,
                    fontSize = dimensionResource(id = R.dimen.current_weather_info_apparent_font_size).value.sp,
                    modifier = Modifier
                        .weight(Float.MAX_WEIGHT)
                        .alignByBaseline()
                )
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.alignByBaseline()
                ) {
                    Icon(
                        painter = painterResource(currentWeather.icon),
                        contentDescription = stringResource(id = R.string.weather_icon_content_description),
                        tint = Color.Unspecified,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.current_weather_info_icon_size))
                    )
                }
            }
        }
    }
}