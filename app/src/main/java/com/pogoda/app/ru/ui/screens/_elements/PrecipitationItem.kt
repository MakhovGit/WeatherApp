package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pogoda.app.ru.R
import com.pogoda.app.ru.ui.theme.IndicatorEmptyColor
import com.pogoda.app.ru.ui.theme.IndicatorFilledColor
import com.pogoda.app.ru.utils.HUNDRED
import com.pogoda.app.ru.utils.HorizontalSpacer
import com.pogoda.app.ru.utils.MAX_WEIGHT

@Composable
fun PrecipitationItem(
    time: String,
    probability: Int,
    probabilityUnit: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            textAlign = TextAlign.Start,
            text = time,
            modifier = Modifier.defaultMinSize(minWidth = 50.dp)
        )
        HorizontalSpacer(dimensionResource(id = R.dimen.spacer_10))
        Box(
            modifier = Modifier.weight(Float.MAX_WEIGHT)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.card_rounded_corner)))
                    .fillMaxWidth()
                    .background(color = IndicatorEmptyColor)
                    .height(dimensionResource(id = R.dimen.precipitation_item_bar_height))
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.card_rounded_corner)))
                        .fillMaxWidth((probability / Float.HUNDRED))
                        .fillMaxHeight()
                        .background(color = IndicatorFilledColor)
                )
            }
        }
        HorizontalSpacer(dimensionResource(id = R.dimen.spacer_10))
        Text(
            textAlign = TextAlign.End,
            text = probability.toString() + probabilityUnit,
            modifier = Modifier.defaultMinSize(
                minWidth = dimensionResource(id = R.dimen.precipitation_item_probability_min_width)
            )
        )
    }
}