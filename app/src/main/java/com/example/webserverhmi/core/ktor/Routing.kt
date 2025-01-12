package com.example.webserverhmi.core.ktor

import io.ktor.server.application.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}