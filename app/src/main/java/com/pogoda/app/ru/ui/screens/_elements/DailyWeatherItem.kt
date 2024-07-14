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
import com.pogoda.app.ru.R
import com.pogoda.app.ru.ui.theme.InterfaceBlockColor
import com.pogoda.app.ru.utils.MAX_WEIGHT

@Composable
fun DailyWeatherItem() {
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
                    text = "Сегодня",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Солнечно",
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
                        text = "3" + stringResource(id = R.string.degree),
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = "-2" + stringResource(id = R.string.degree),
                        textAlign = TextAlign.End
                    )
                }
                VerticalDivider(
                    color = Color.Black,
                    modifier = Modifier.height(dimensionResource(id = R.dimen.daily_weather_item_divider_height))
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.weather_02),
                contentDescription = stringResource(id = R.string.weather_icon_content_description),
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.daily_weather_item_icon_size))
                    .padding(end = dimensionResource(id = R.dimen.daily_weather_item_icon_padding))
            )
        }
    }
}