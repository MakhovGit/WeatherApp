package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.pogoda.app.ru.R
import com.pogoda.app.ru.ui.theme.InterfaceBlockColor
import com.pogoda.app.ru.utils.HorizontalSpacer
import com.pogoda.app.ru.utils.MAX_WEIGHT
import com.pogoda.app.ru.utils.VerticalSpacer

@Composable
fun PrecipitationInfo() {
    Surface(
        color = InterfaceBlockColor,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_rounded_corner)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.common_padding))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                dimensionResource(id = R.dimen.precipitation_info_padding)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.precipitation_01),
                    contentDescription = stringResource(id = R.string.weather_icon_content_description),
                    tint = Color.Unspecified
                )
                HorizontalSpacer(width = dimensionResource(id = R.dimen.spacer_10))
                Text(
                    text = stringResource(id = R.string.precipitation_probability),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(Float.MAX_WEIGHT)
                )
            }
            VerticalSpacer(dimensionResource(id = R.dimen.spacer_20))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.spacer_10)
                )
            ) {
                repeat(4) {
                    item {
                        PrecipitationItem(precipitation = Pair("10:00", 40))
                    }
                }
            }
            VerticalSpacer(height = dimensionResource(id = R.dimen.spacer_20))
        }
    }
}