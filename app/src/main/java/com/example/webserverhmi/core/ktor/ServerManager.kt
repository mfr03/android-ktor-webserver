package com.example.webserverhmi.core.ktor

import io.ktor.server.application.Application
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.utils.io.CancellationException
import io.ktor.utils.io.errors.IOException
import io.netty.channel.ChannelException
import io.netty.handler.timeout.TimeoutException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.BindException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerManager @Inject constructor() {
    private val servers = mutableMapOf<
            Int,
            EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>
            >()

    private val gracePeriodMills = 1000L
    private val timeoutPeriodMills = 2000L
    private var isServerRunning = false;

    fun startServer(host: String, port: Int, module: Application.() -> Unit): String? {

        if (servers.containsKey(port)) {
            return "Server is already running on port $port"
        }

        try {

            val server = embeddedServer(Netty, port = port, host = host, module = module)
            servers[port] = server

            server.start(wait = false)

            return "Attempt to start"
        }  catch (e: Exception) {
            servers.remove(port)
            throw e
        }
    }

    fun stopServer(port: Int): String {

        try {
            if (isServerRunning) {
                servers[port]?.stop(gracePeriodMillis = gracePeriodMills,
                    timeoutMillis = timeoutPeriodMills)
                servers.remove(port)
                return "Server stopped successfully."
            } else {
                return "Server is already stopped."
            }

        } catch (e: Exception) {
            throw e
        }

    }

    fun stopAllServers() {
        servers.values.forEach { it.stop(gracePeriodMillis = gracePeriodMills, timeoutMillis = timeoutPeriodMills) }
        servers.clear()
    }

    fun serverSuccessfullyStarted(host: String, port: Int): String {
        isServerRunning = true
        return "Webserver $host at port $port has successfully started"
    }

    fun serverStopped(host: String, port: Int): String {
        isServerRunning = false
        return "Webserver $host at port $port has stopped"
    }


}