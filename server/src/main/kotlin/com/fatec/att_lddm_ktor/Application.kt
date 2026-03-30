package com.fatec.att_lddm_ktor

import com.fatec.att_lddm_ktor.database.DatabaseFactory
import com.fatec.att_lddm_ktor.repository.FootballRepository
import com.fatec.att_lddm_ktor.routes.teamRoutes
import com.fatec.att_lddm_ktor.routes.playerRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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

        teamRoutes(repository)
        playerRoutes(repository)

        swaggerUI(path = "swagger", swaggerFile = "documentation.yaml") {
            version = "4.15.5"
        }
    }
}