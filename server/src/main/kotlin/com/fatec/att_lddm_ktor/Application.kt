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
    // Inicializa o Banco
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
            call.respondText("API de Times de Futebol está Online!")
        }

        get("/times") {
            try {
                val listaDeTimes = repository.allTeams()
                call.respond(listaDeTimes)
            } catch (e: Exception) {
                println("Erro ao buscar times: ${e.message}")
                call.respond(emptyList<Map<String, Any>>())
            }
        }

        // Swagger configurado
        swaggerUI(path = "swagger", swaggerFile = "documentation.yaml") {
            version = "4.15.5"
        }
    }
}