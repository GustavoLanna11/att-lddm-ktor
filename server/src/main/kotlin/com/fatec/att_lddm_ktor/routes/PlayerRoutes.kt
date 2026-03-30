package com.fatec.att_lddm_ktor.routes

import com.fatec.att_lddm_ktor.repository.FootballRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.playerRoutes(repository: FootballRepository) {
    route("/jogadores") {
        get {
            try {
                call.respond(repository.allPlayers())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erro ao buscar jogadores")
            }
        }

        post {
            try {
                val nome = call.parameters["nome"] ?: ""
                val posicao = call.parameters["posicao"] ?: ""
                val teamId = call.parameters["teamId"]?.toIntOrNull() ?: 0

                repository.createPlayer(nome, posicao, teamId)
                call.respond(HttpStatusCode.Created, "Jogador $nome cadastrado!")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Erro ao cadastrar jogador. Verifique se o teamId existe.")
            }
        }

        put("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                val nome = call.parameters["nome"] ?: ""
                val posicao = call.parameters["posicao"] ?: ""

                if (id != null) {
                    repository.updatePlayer(id, nome, posicao)
                    call.respond(HttpStatusCode.OK, "Jogador $id atualizado!")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erro ao atualizar")
            }
        }

        delete("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id != null) {
                    repository.deletePlayer(id)
                    call.respond(HttpStatusCode.OK, "Jogador removido!")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erro ao deletar")
            }
        }
    }
}