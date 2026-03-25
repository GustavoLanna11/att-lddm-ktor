package com.fatec.att_lddm_ktor.repository

import com.fatec.att_lddm_ktor.database.*
import com.fatec.att_lddm_ktor.database.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class FootballRepository {
    // Busca todos os times do banco
    suspend fun allTeams(): List<String> = dbQuery {
        TeamsTable.selectAll().map { it[TeamsTable.nome] }
    }
}