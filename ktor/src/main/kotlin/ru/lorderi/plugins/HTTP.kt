package ru.lorderi.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen

fun Application.configureHTTP() {
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml") {
            codegen = StaticHtmlCodegen()
        }
    }
}
