package com.pogoda.app.ru.ui.screens._elements

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pogoda.app.ru.R
import com.pogoda.app.ru.ui.theme.ButtonNotPressedColor
import com.pogoda.app.ru.ui.theme.ButtonPressedColor
import com.pogoda.app.ru.ui.theme.ButtonTextColor

@Composable
fun SimpleButton(
    modifier: Modifier,
    text: String,
    isPressed: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_rounded_corner)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) ButtonPressedColor else ButtonNotPressedColor,
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = ButtonTextColor
        )
    }
}