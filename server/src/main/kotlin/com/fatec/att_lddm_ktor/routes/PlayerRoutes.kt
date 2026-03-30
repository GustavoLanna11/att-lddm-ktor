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
                call.respondText(text = repository.allPlayers(), contentType = ContentType.Text.Plain)
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
                call.respond(HttpStatusCode.BadRequest, "Erro ao cadastrar jogador.")
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
                    val qtdDeletada = repository.deletePlayer(id)
                    if (qtdDeletada > 0) {
                        call.respond(HttpStatusCode.OK, "Jogador removido com sucesso!")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Jogador com ID $id não encontrado!")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erro ao deletar")
            }
        }
    }
}