package com.pogoda.app.ru.ui.screens._elements

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pogoda.app.ru.R
import com.pogoda.app.ru.utils.VerticalSpacer

@Composable
fun HourlyWeatherItem(time: String, @DrawableRes icon: Int, temp: String) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = time,
                fontSize = dimensionResource(id = R.dimen.hourly_weather_item_time_font_size).value.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            VerticalSpacer(dimensionResource(id = R.dimen.spacer_10))
            Icon(
                painter = painterResource(icon),
                contentDescription = stringResource(id = R.string.weather_icon_content_description),
                tint = Color.Unspecified,
                modifier = Modifier.size(dimensionResource(id = R.dimen.hourly_weather_item_icon_size))
            )
            VerticalSpacer(dimensionResource(id = R.dimen.spacer_10))
            Text(
                text = temp + stringResource(id = R.string.degree),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}