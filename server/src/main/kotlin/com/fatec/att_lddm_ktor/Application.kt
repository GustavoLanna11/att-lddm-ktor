package com.fatec.att_lddm_ktor

import com.fatec.att_lddm_ktor.database.DatabaseFactory
import com.fatec.att_lddm_ktor.repository.FootballRepository
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // 1. Inicializa o Banco com segurança
    try {
        DatabaseFactory.init()
        println("✅ BANCO DE DADOS CONECTADO!")
    } catch (e: Exception) {
        println("❌ ERRO AO CONECTAR NO BANCO: ${e.message}")
    }

    val repository = FootballRepository()

    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText("API de Times de Futebol está Online!")
        }

        get("/times") {
            try {
                val times = repository.allTeams()
                call.respond(times)
            } catch (e: Exception) {
                call.respondText("Erro ao buscar times: ${e.message}")
            }
        }

        // 2. Swagger comentado até você criar o arquivo .yaml
        /*
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        */
    }
}