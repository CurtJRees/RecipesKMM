package com.curtjrees.recipes.server

import com.curtjrees.recipes.sharedCore.ApiRecipesResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    //Database setup
    setupDatabase()
    setupServer()
}

private fun setupServer() {
    val port = System.getenv("PORT")?.toInt() ?: 9090
    embeddedServer(factory = Netty, port = port) {
        install(ContentNegotiation) {
            json()
        }

        routing {

            get("/recipes") {
                val dbRecipes = transaction { DbRecipe.all().toList() }
                val apiRecipes = dbRecipes.map {
                    DbApiMapper.map(it)
                }

                val response = ApiRecipesResponse(
                    status = 200,
                    data = apiRecipes
                )
                call.respond(response)
            }

        }
    }.start(wait = true)
}

private fun setupDatabase() {
    Database.connect(System.getenv("JDBC_DATABASE_URL"), driver = "org.postgresql.Driver")
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Recipes)
        println("Recipes: ${DbRecipe.all().joinToString { it.name }}")
    }
}


object Recipes : LongIdTable() {
    val name = varchar("name", 255)
    val imageUrl = varchar("image_url", 255).nullable()
}

class DbRecipe(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<DbRecipe>(Recipes)

    var name by Recipes.name
    var imageUrl by Recipes.imageUrl
}