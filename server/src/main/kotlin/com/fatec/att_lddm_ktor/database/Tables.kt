package com.fatec.att_lddm_ktor.database

import org.jetbrains.exposed.sql.*
// Tabela de Times de Futebol
object TeamsTable : Table("teams") {
    val id = integer("id").autoIncrement()
    val nome = varchar("nome", 50)
    val estadio = varchar("estadio", 50)
    val cidade = varchar("cidade", 50)

    override val primaryKey = PrimaryKey(id)
}

// Tabela de Jogadores
object PlayersTable : Table("players") {
    val id = integer("id").autoIncrement()
    val nome = varchar("nome", 50)
    val posicao = varchar("posicao", 30)
    val teamId = integer("team_id") references TeamsTable.id

    override val primaryKey = PrimaryKey(id)
}