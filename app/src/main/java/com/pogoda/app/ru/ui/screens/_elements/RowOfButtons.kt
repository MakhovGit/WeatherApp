package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pogoda.app.ru.R
import com.pogoda.app.ru.utils.MAX_WEIGHT

@Composable
fun RowOfButtons(
    isTodayButtonPressed: Boolean,
    isTomorrowButtonPressed: Boolean,
    is10daysButtonPressed: Boolean,
    onTodayButtonPressed: () -> Unit,
    onTomorrowButtonPressed: () -> Unit,
    on10daysButtonPressed: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.spacer_20)
        )
    ) {
        SimpleButton(
            modifier = Modifier.weight(Float.MAX_WEIGHT),
            text = stringResource(id = R.string.button_today),
            isPressed = isTodayButtonPressed
        ) {
            onTodayButtonPressed()
        }
        SimpleButton(
            modifier = Modifier.weight(Float.MAX_WEIGHT),
            text = stringResource(id = R.string.button_tomorrow),
            isPressed = isTomorrowButtonPressed
        ) {
            onTomorrowButtonPressed()
        }
        SimpleButton(
            modifier = Modifier.weight(Float.MAX_WEIGHT),
            text = stringResource(id = R.string.button_10days),
            isPressed = is10daysButtonPressed
        ) {
            on10daysButtonPressed()
        }
    }
}