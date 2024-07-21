package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.pogoda.app.ru.R
import com.pogoda.app.ru.model.places.PlacesInfo
import com.pogoda.app.ru.ui.theme.SearchStringColor
import com.pogoda.app.ru.ui.theme.SearchStringIconColor
import com.pogoda.app.ru.ui.theme.SearchStringIndicatorColor
import com.pogoda.app.ru.ui.theme.SearchStringPlaceHolderColor
import com.pogoda.app.ru.utils.COMMA
import com.pogoda.app.ru.utils.EMPTY
import com.pogoda.app.ru.utils.FIVE_HUNDRED
import com.pogoda.app.ru.utils.ONE
import com.pogoda.app.ru.utils.SPACE
import com.pogoda.app.ru.utils.VerticalSpacer
import com.pogoda.app.ru.utils.ZERO
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    places: PlacesInfo,
    isPlacesLoaded: Boolean,
    requestPlaces: (placeName: String) -> Unit,
    requestWeather: (latitude: Double, longitude: Double, placeName: String) -> Unit,
    erasePlaces: () -> Unit
) {
    var searchString by remember { mutableStateOf(String.EMPTY) }
    val textFieldShape =
        RoundedCornerShape(dimensionResource(id = R.dimen.card_rounded_corner))
    var isMenuExpanded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = searchString) {
        if (searchString.isNotEmpty()) {
            delay(Long.FIVE_HUNDRED)
            requestPlaces(searchString)
        }
    }
    LaunchedEffect(key1 = places.places) {
        isMenuExpanded = isPlacesLoaded && places.places.size > Int.ZERO
    }
    VerticalSpacer(dimensionResource(id = R.dimen.spacer_30))
    ExposedDropdownMenuBox(
        expanded = isMenuExpanded,
        onExpandedChange = { status ->
            if (status) {
                isMenuExpanded = isPlacesLoaded
            } else {
                isMenuExpanded = false
                erasePlaces()
            }
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
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
                focusedIndicatorColor = SearchStringIndicatorColor,
                unfocusedIndicatorColor = SearchStringIndicatorColor
            ),
            shape = textFieldShape,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_placeholder),
                    color = SearchStringPlaceHolderColor
                )
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = {
                isMenuExpanded = false
                erasePlaces()
            },
            modifier = Modifier
                .background(Color.White)
                .requiredHeightIn(max = dimensionResource(id = R.dimen.search_max_menu_height))
                .exposedDropdownSize()
        ) {
            places.places.forEachIndexed { index, place ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = buildString {
                                append(place.name)
                                append(String.COMMA)
                                append(String.SPACE)
                                append(place.country)
                                if (place.admin1.isNotEmpty()) {
                                    append(String.COMMA)
                                    append(String.SPACE)
                                    append(place.admin1)
                                }
                                if (place.admin2.isNotEmpty()) {
                                    append(String.COMMA)
                                    append(String.SPACE)
                                    append(place.admin2)
                                }
                                if (place.admin3.isNotEmpty()) {
                                    append(String.COMMA)
                                    append(String.SPACE)
                                    append(place.admin3)
                                }
                                if (place.admin4.isNotEmpty()) {
                                    append(String.COMMA)
                                    append(String.SPACE)
                                    append(place.admin4)
                                }
                            }
                        )
                    },
                    onClick = {
                        searchString = String.EMPTY
                        val placeName = buildString {
                            append(place.name)
                            append(String.COMMA)
                            append(String.SPACE)
                            append(place.country)

                        }
                        requestWeather(place.latitude, place.longitude, placeName)
                        isMenuExpanded = false
                        erasePlaces()
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_place_24),
                            contentDescription = stringResource(id = R.string.address_icon_content_description),
                            tint = Color.Unspecified
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.search_menu_item_content_padding_vertical))
                )
                if (index < (places.places.size - Int.ONE)) {
                    HorizontalDivider()
                }
            }
        }
    }
    VerticalSpacer(dimensionResource(id = R.dimen.spacer_30))
}