package com.fatec.att_lddm_ktor

import com.fatec.att_lddm_ktor.database.DatabaseFactory
import com.fatec.att_lddm_ktor.repository.FootballRepository
import com.fatec.att_lddm_ktor.routes.teamRoutes
import com.fatec.att_lddm_ktor.routes.playerRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
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
        println("✅ BANCO DE DADOS CONECTADO COM SUCESSO!")
    } catch (e: Exception) {
        println("❌ ERRO AO CONECTAR NO BANCO: ${e.message}")
    }

    val repository = FootballRepository()

    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText("API de Futebol FATEC está Online!")
        }

        // 1. Chama as rotas de Times (CRUD completo está lá dentro)
        teamRoutes(repository)

        // 2. Chama as rotas de Jogadores (Para cumprir o requisito de 2 arquivos)
        playerRoutes(repository)

        // 3. Swagger
        swaggerUI(path = "swagger", swaggerFile = "documentation.yaml") {
            version = "4.15.5"
        }
    }
}