package ru.lorderi.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
//        gson {
//            setPrettyPrinting()
//            serializeNulls()
//        }
        json()
    }
//    routing {
//        get("/json/gson") {
//            call.respond(mapOf("hello" to "world"))
//        }
//        get("/json/kotlinx-serialization") {
//            call.respond(mapOf("hello" to "world"))
//        }
//    }
}
