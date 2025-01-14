package com.example.webserverhmi.data.server.repository

import com.example.webserverhmi.core.ktor.ServerManager
import com.example.webserverhmi.core.ktor.configureRouting
import com.example.webserverhmi.core.ktor.configureSerialization
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.application.ServerReady
import io.ktor.utils.io.CancellationException
import io.ktor.utils.io.errors.IOException
import io.netty.channel.ChannelException
import io.netty.handler.timeout.TimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.BindException
import java.net.NetworkInterface
import java.net.SocketException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class ServerRepository @Inject constructor(
    private val serverManager: ServerManager
): ServerRepositoryInterface {

    override suspend fun getLocalIp(): Result<String> {
        return try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            var res = "0.0.0.0"
            for (intf in interfaces) {
                val addresses = intf.inetAddresses
                for (address in addresses) {
                    if (!address.isLoopbackAddress && address is java.net.Inet4Address) {
                        res = address.hostAddress
                        break
                    }
                }
            }
            return Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun startServer(hostAddress: String,
                                     hostPort: Int,
                                     uiEvent: (String, Boolean) -> Unit
    ): Result<String> {

        return withContext(Dispatchers.IO) {
            try {
                suspendCoroutine { continuation ->
                    val result = serverManager.startServer(host = hostAddress, port = hostPort) {
                        monitor.subscribe(ServerReady) {
                            val res = serverManager.serverSuccessfullyStarted(hostAddress, hostPort)
                            uiEvent(res, true)
                            continuation.resume(Result.success(res))
                        }

                        monitor.subscribe(ApplicationStopped) {
                            val res = serverManager.serverStopped(hostAddress, hostPort)
                            monitor.unsubscribe(ServerReady) {}
                            monitor.unsubscribe(ApplicationStopped) {}
                            uiEvent(res, false)
                        }
                        configureRouting()
                        configureSerialization()
                    }

                    if(result != "Attempt to start") {
                        continuation.resume(Result.failure(Exception(result)))
                    }
                }
            } catch (e: BindException) {
                Result.failure(Exception("Invalid host address: ${e.message}"))
            } catch (e: IllegalArgumentException) {
                Result.failure(Exception("Invalid argument: ${e.message}"))
            } catch (e: ChannelException) {
                Result.failure(Exception("Network channel error: ${e.message}"))
            } catch (e: SecurityException) {
                Result.failure(Exception("Security error: ${e.message}"))
            } catch (e: SocketException) {
                Result.failure(Exception("Unresolved Address"))
            } catch (e: Exception) {
                Result.failure(Exception("Unexpected error: ${e.message}"))
            }
        }

    }

    override suspend fun stopServer(hostPort: Int) : Result<String> {

        return withContext(Dispatchers.IO) {
            try{
                suspendCoroutine { continuation ->
                    val res = serverManager.stopServer(hostPort)
                    continuation.resume(Result.success(res))
                }
            } catch (e: IllegalStateException) {
                Result.failure(Exception("Illegal state: ${e.message}"))
            } catch (e: TimeoutException) {
                Result.failure(Exception("Timeout during shutdown: ${e.message}"))
            } catch (e: IOException) {
                Result.failure(Exception("I/O exception: ${e.message}"))
            } catch (e: CancellationException) {
                Result.failure(Exception("Shutdown canceled: ${e.message}"))
            } catch (e: Exception) {
                Result.failure(Exception("Unexpected exception: ${e.message}"))
            }

        }

    }

}