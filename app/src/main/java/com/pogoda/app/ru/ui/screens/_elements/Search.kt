package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.pogoda.app.ru.R
import com.pogoda.app.ru.ui.theme.AltBackgroundColor
import com.pogoda.app.ru.ui.theme.SearchStringColor
import com.pogoda.app.ru.ui.theme.SearchStringIconColor
import com.pogoda.app.ru.ui.theme.SearchStringPlaceHolderColor
import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.VerticalSpacer

@Composable
fun Search(
    isTodayButtonPressed: Boolean,
    isTomorrowButtonPressed: Boolean,
    is10daysButtonPressed: Boolean,
    onTodayButtonPressed: () -> Unit,
    onTomorrowButtonPressed: () -> Unit,
    on10daysButtonPressed: () -> Unit
) {
    var searchString by remember { mutableStateOf(String.EMPTY) }
    val textFieldShape =
        RoundedCornerShape(dimensionResource(id = R.dimen.card_rounded_corner))
    Surface(
        color = AltBackgroundColor,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = dimensionResource(id = R.dimen.search_elevation))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.common_padding))
        ) {
            VerticalSpacer(dimensionResource(id = R.dimen.spacer_30))
            TextField(
                value = searchString,
                onValueChange = { newValue ->
                    searchString = newValue
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = stringResource(id = R.string.search_icon_content_description),
                        tint = SearchStringIconColor
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SearchStringColor,
                    unfocusedContainerColor = SearchStringColor,
                    focusedIndicatorColor = AltBackgroundColor,
                    unfocusedIndicatorColor = AltBackgroundColor
                ),
                shape = textFieldShape,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_placeholder),
                        color = SearchStringPlaceHolderColor
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            VerticalSpacer(dimensionResource(id = R.dimen.spacer_30))
            MainWeatherInfo()
            VerticalSpacer(dimensionResource(id = R.dimen.spacer_10))
            RowOfButtons(
                isTodayButtonPressed = isTodayButtonPressed,
                isTomorrowButtonPressed = isTomorrowButtonPressed,
                is10daysButtonPressed = is10daysButtonPressed,
                onTodayButtonPressed = onTodayButtonPressed,
                onTomorrowButtonPressed = onTomorrowButtonPressed,
                on10daysButtonPressed = on10daysButtonPressed
            )
            VerticalSpacer(dimensionResource(id = R.dimen.spacer_20))
        }
    }
}