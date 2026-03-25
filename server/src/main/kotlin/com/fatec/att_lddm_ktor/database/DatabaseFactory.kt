package com.fatec.att_lddm_ktor.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcUrl = "jdbc:postgresql://localhost:5432/futebol_db"
        val user = "admin"
        val password = "password123"

        val database = Database.connect(jdbcUrl, driverClassName, user, password)

        transaction(database) {
            // Isso cria as tabelas no Postgres (Migration)
            SchemaUtils.create(TeamsTable, PlayersTable)

            // Seed Data: Insere times se o banco estiver vazio
            if (TeamsTable.selectAll().empty()) {
                TeamsTable.insert {
                    it[nome] = "Palmeiras"
                    it[estadio] = "Allianz Parque"
                    it[cidade] = "São Paulo"
                }
                TeamsTable.insert {
                    it[nome] = "Corinthians"
                    it[estadio] = "Neo Química Arena"
                    it[cidade] = "São Paulo"
                }
            }
        }
    }

    // Função auxiliar para rodar comandos no banco de forma eficiente
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}