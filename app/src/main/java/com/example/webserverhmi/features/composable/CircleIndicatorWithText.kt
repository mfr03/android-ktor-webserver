package com.example.webserverhmi.features.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircleIndicatorWithText(
    indicatorText: String,
    primaryBorderColor: Color,
    primaryBackgroundColor: Color,
    errorBorderColor: Color,
    errorBackgroundColor: Color,
    statusState: Boolean,
)
{
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = indicatorText)
        if(statusState)
        {
            Box(
                modifier = Modifier
                    .border(
                        width = 6.dp,
                        color = primaryBorderColor,
                        shape = CircleShape
                    )
                    .size(height = 32.dp, width = 32.dp)
                    .background(primaryBackgroundColor, shape = CircleShape)
            )
        }
        else
        {
            Box(
                modifier = Modifier
                    .border(
                        width = 6.dp,
                        color = errorBorderColor,
                        shape = CircleShape
                    )
                    .size(height = 32.dp, width = 32.dp)
                    .background(errorBackgroundColor, shape = CircleShape)
            )
        }

    }
}