package com.example.webserverhmi.core.ktor

import io.ktor.server.application.Application
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServerManager {
    private val servers = mutableMapOf<
            Int,
            EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>
            >()

    private val gracePeriodMills = 1000L
    private val timeoutPeriodMills = 2000L

    fun startServer(port: Int, module: Application.() -> Unit) {

        if (servers.containsKey(port)) {
            throw IllegalStateException("Server is already running on port $port")
        }

        val server = embeddedServer(Netty, port = port, host = "0.0.0.0", module = module)

        servers[port] = server
        CoroutineScope(Dispatchers.IO).launch {
            server.start(wait = false)
        }
    }

    fun stopServer(port: Int) {
        servers[port]?.stop(gracePeriodMillis = gracePeriodMills, timeoutMillis = timeoutPeriodMills)
        servers.remove(port)
    }

    fun stopAllServers() {
        servers.values.forEach { it.stop(gracePeriodMillis = gracePeriodMills, timeoutMillis = timeoutPeriodMills) }
        servers.clear()
    }


}