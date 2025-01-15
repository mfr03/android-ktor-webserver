package com.example.webserverhmi.data.server.repository

interface ServerRepository {
    suspend fun getLocalIp(): Result<String>
    suspend fun startServer(hostAddress: String, hostPort: Int, uiEvent: (String, Boolean) -> Unit): Result<String>
    suspend fun stopServer(hostPort: Int): Result<String>
}