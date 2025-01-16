package com.example.webserverhmi.features.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun preview(){
    RoutingItem(routing = "/house_is_burning_down", routingType = "GET")
    RoutingItem(routing = "/house_is_burning_down", routingType = "POST")
}


@Composable
fun RoutingItem(routing: String, routingType: String){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            shape = RoundedCornerShape(16.dp)
        )
        .padding(horizontal = 16.dp, vertical = 8.dp)
        ,

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = routingType, color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = FontWeight.Bold)
            Text(text = routing, color = MaterialTheme.colorScheme.onTertiaryContainer)
        }
    }
}