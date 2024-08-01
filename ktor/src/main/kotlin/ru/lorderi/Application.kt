package ru.lorderi

import io.ktor.server.application.*
import ru.lorderi.dao.DatabaseSingleton
import ru.lorderi.plugins.configureHTTP
import ru.lorderi.plugins.configureRouting
import ru.lorderi.plugins.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseSingleton.init()
    configureSerialization()
    configureHTTP()
    configureRouting()
}
