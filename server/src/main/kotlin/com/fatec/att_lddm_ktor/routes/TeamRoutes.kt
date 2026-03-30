package com.fatec.att_lddm_ktor.routes

import com.fatec.att_lddm_ktor.repository.FootballRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.teamRoutes(repository: FootballRepository) {
    route("/times") {
        get {
            try {
                val listaDeTimes = repository.allTeams()
                call.respondText(text = listaDeTimes, contentType = ContentType.Text.Plain)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erro ao buscar times")
            }
        }

        post {
            try {
                val nome = call.parameters["nome"] ?: ""
                val estadio = call.parameters["estadio"] ?: ""
                val cidade = call.parameters["cidade"] ?: ""
                repository.createTeam(nome, estadio, cidade)
                call.respond(HttpStatusCode.Created, "Time $nome cadastrado!")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Dados inválidos")
            }
        }

        put("/{id}") {
            try {
                val id = call.parameters["id"]?.toIntOrNull()
                val nome = call.parameters["nome"] ?: ""
                val estadio = call.parameters["estadio"] ?: ""
                val cidade = call.parameters["cidade"] ?: ""

                if (id != null) {
                    repository.updateTeam(id, nome, estadio, cidade)
                    call.respond(HttpStatusCode.OK, "Time atualizado com sucesso!")
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
                    val qtdDeletada = repository.deleteTeam(id)
                    if (qtdDeletada > 0) {
                        call.respond(HttpStatusCode.OK, "Time removido com sucesso!")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Time com ID $id não encontrado!")
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