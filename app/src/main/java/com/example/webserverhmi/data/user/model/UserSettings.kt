package com.example.webserverhmi.data.user.model

import com.example.webserverhmi.data.server.model.WebServer
import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val userWebServerSettings: WebServer = WebServer(),
    val routings: List<Pair<String, String>> = listOf(Pair("/", "GET")),
)