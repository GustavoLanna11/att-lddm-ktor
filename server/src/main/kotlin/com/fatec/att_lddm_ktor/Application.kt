package com.fatec.att_lddm_ktor

import com.fatec.att_lddm_ktor.database.DatabaseFactory
import com.fatec.att_lddm_ktor.repository.FootballRepository
import com.fatec.att_lddm_ktor.routes.teamRoutes
import com.fatec.att_lddm_ktor.routes.playerRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    try {
        DatabaseFactory.init()
        println("✅ BANCO DE DADOS CONECTADO!")
    } catch (e: Exception) {
        println("❌ ERRO NO BANCO: ${e.message}")
    }

    val repository = FootballRepository()

    routing {
        get("/") {
            call.respondText("API Online!")
        }

        teamRoutes(repository)
        playerRoutes(repository)

        swaggerUI(path = "swagger", swaggerFile = "documentation.yaml")
    }
}