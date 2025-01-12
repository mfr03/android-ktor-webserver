package com.example.webserverhmi.core.ktor

import io.ktor.server.application.*

fun Application.module()
{
    configureRouting()
    configureSerialization()
}