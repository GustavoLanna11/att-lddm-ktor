package com.fatec.att_lddm_ktor.repository

import com.fatec.att_lddm_ktor.database.TeamsTable
import com.fatec.att_lddm_ktor.database.PlayersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class FootballRepository {

    fun allTeams(): String = transaction {
        val times = TeamsTable.selectAll().map {
            "ID: ${it[TeamsTable.id]} | Nome: ${it[TeamsTable.nome]} | Estádio: ${it[TeamsTable.estadio]} | Cidade: ${it[TeamsTable.cidade]}"
        }
        if (times.isEmpty()) "Nenhum time cadastrado." else times.joinToString("\n")
    }

    fun allPlayers(): String = transaction {
        val jogadores = PlayersTable.selectAll().map {
            "ID: ${it[PlayersTable.id]} | Nome: ${it[PlayersTable.nome]} | Posição: ${it[PlayersTable.posicao]} | TimeID: ${it[PlayersTable.teamId]}"
        }
        if (jogadores.isEmpty()) "Nenhum jogador cadastrado." else jogadores.joinToString("\n")
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

    fun createPlayer(nomeP: String, posicaoP: String, idDoTime: Int) = transaction {
        PlayersTable.insert { it[nome] = nomeP; it[posicao] = posicaoP; it[teamId] = idDoTime }
    }

    fun updatePlayer(idP: Int, nomeP: String, posicaoP: String) = transaction {
        PlayersTable.update({ PlayersTable.id eq idP }) { it[nome] = nomeP; it[posicao] = posicaoP }
    }

    fun deletePlayer(idP: Int) = transaction {
        PlayersTable.deleteWhere { PlayersTable.id eq idP }
    }
}