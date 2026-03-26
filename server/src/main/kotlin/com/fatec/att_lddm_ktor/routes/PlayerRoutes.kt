package com.fatec.att_lddm_ktor.routes

import com.fatec.att_lddm_ktor.repository.FootballRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.playerRoutes(repository: FootballRepository) {
    route("/jogadores") {
        get {
            call.respond(listOf(mapOf("nome" to "Neymar", "posicao" to "Atacante")))
        }
        post { call.respondText("Jogador criado!") }
        put("/{id}") { call.respondText("Jogador atualizado!") }
        delete("/{id}") { call.respondText("Jogador removido!") }
    }
}