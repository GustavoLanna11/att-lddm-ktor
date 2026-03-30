package com.fatec.att_lddm_ktor.repository

import com.fatec.att_lddm_ktor.database.TeamsTable
import com.fatec.att_lddm_ktor.database.PlayersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class FootballRepository {

    // Times
    fun allTeams(): List<Map<String, Any>> = transaction {
        TeamsTable.selectAll().map {
            mapOf("id" to it[TeamsTable.id], "nome" to it[TeamsTable.nome], "estadio" to it[TeamsTable.estadio], "cidade" to it[TeamsTable.cidade])
        }
    }

    fun createTeam(nomeT: String, estadioT: String, cidadeT: String) = transaction {
        TeamsTable.insert { it[nome] = nomeT; it[estadio] = estadioT; it[cidade] = cidadeT }
    }

    fun updateTeam(idT: Int, nomeT: String, estadioT: String, cidadeT: String) = transaction {
        TeamsTable.update({ TeamsTable.id eq idT }) { it[nome] = nomeT; it[estadio] = estadioT; it[cidade] = cidadeT }
    }

    fun deleteTeam(idT: Int) = transaction {
        TeamsTable.deleteWhere { TeamsTable.id eq idT }
    }

    // Jogadores
    fun allPlayers(): List<Map<String, Any>> = transaction {
        PlayersTable.selectAll().map {
            mapOf("id" to it[PlayersTable.id], "nome" to it[PlayersTable.nome], "posicao" to it[PlayersTable.posicao], "teamId" to it[PlayersTable.teamId])
        }
    }

    fun createPlayer(nomeP: String, posicaoP: String, idDoTime: Int) = transaction {
        PlayersTable.insert { it[nome] = nomeP; it[posicao] = posicaoP; it[teamId] = idDoTime }
    }

    fun updatePlayer(idP: Int, nomeP: String, posicaoP: String) = transaction {
        PlayersTable.update({ PlayersTable.id eq idP }) {
            it[nome] = nomeP
            it[posicao] = posicaoP
        }
    }

    fun deletePlayer(idP: Int) = transaction {
        PlayersTable.deleteWhere { PlayersTable.id eq idP }
    }
}