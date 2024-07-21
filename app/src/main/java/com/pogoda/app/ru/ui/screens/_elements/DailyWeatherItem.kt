package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import com.pogoda.app.ru.model.weather.DailyWeatherInfo
import com.pogoda.app.ru.ui.theme.InterfaceBlockColor
import com.pogoda.app.ru.utils.MAX_WEIGHT
import com.pogoda.app.ru.utils.ZERO

@Composable
fun DailyWeatherItem(
    dailyWeather: DailyWeatherInfo,
    index: Int
) {
    Surface(
        color = InterfaceBlockColor,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_rounded_corner))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_30)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.daily_weather_item_padding))
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_10)),
                modifier = Modifier.weight(Float.MAX_WEIGHT)
            ) {
                Text(
                    text = if (index == Int.ZERO)
                        stringResource(id = R.string.button_today) else dailyWeather.date,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dimensionResource(id = R.dimen.daily_weather_item_date_font_size).value.sp,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = dailyWeather.description,
                    fontSize = dimensionResource(id = R.dimen.daily_weather_item_description_font_size).value.sp,
                    textAlign = TextAlign.Start
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_10)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_10))
                ) {
                    Text(
                        text = dailyWeather.temperature,
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = dailyWeather.apparentTemperature,
                        textAlign = TextAlign.End
                    )
                }
                VerticalDivider(
                    color = Color.Black,
                    modifier = Modifier.height(dimensionResource(id = R.dimen.daily_weather_item_divider_height))
                )
            }
            Icon(
                painter = painterResource(id = dailyWeather.icon),
                contentDescription = stringResource(id = R.string.weather_icon_content_description),
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.daily_weather_item_icon_size))
                    .padding(end = dimensionResource(id = R.dimen.daily_weather_item_icon_padding))
            )
        }
    }
}