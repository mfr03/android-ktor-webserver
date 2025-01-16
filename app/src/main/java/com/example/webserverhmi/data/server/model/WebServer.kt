package com.example.webserverhmi.data.server.model

import kotlinx.serialization.Serializable

@Serializable
data class WebServer(
    val hostAddress: String = "0.0.0.0",
    val hostPort: String = "8080",
)
