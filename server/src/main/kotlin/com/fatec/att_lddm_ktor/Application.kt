package com.fatec.att_lddm_ktor

import com.fatec.att_lddm_ktor.database.DatabaseFactory
import com.fatec.att_lddm_ktor.repository.FootballRepository
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
    // 1. Inicializa o Banco (Cria tabelas e faz o Seed)
    DatabaseFactory.init()

    // 2. Instancia o repositório para usar nas rotas
    val repository = FootballRepository()

    // 3. Configura JSON
    install(ContentNegotiation) {
        json()
    }

    // 4. Configura as Rotas
    routing {
        // Página inicial simples
        get("/") {
            call.respondText("API de Times de Futebol está Online!")
        }

        // Rota para listar os times (testando o banco)
        get("/times") {
            val times = repository.allTeams()
            call.respond(times)
        }

        // 5. Configura o Swagger
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
    }
}