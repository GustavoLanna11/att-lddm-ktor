package com.fatec.att_lddm_ktor.routes

import com.fatec.att_lddm_ktor.repository.FootballRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.teamRoutes(repository: FootballRepository) {

    route("/times") {

        // Lista
        get {
            try {
                val times = repository.allTeams()
                call.respond(times)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Erro ao buscar times")
            }
        }

        // Post
        post {
            try {
                // Aqui o Ktor tenta transformar o JSON que vem no corpo em um objeto ou mapa
                val parameters = call.receiveParameters()
                // Por enquanto, apenas confirmamos o recebimento para o Swagger não dar erro
                call.respond(HttpStatusCode.Created, "Time recebido com sucesso!")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Dados inválidos")
            }
        }

        // Atualizar
        put("/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID é obrigatório")
                return@put
            }
            call.respond(HttpStatusCode.OK, "Time $id atualizado com sucesso!")
        }

        // Delete
        delete("/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID é obrigatório")
                return@delete
            }
            // Aqui você chamaria repository.deleteTeam(id.toInt())
            call.respond(HttpStatusCode.OK, "Time $id removido com sucesso!")
        }
    }
}