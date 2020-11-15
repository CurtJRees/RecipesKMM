package com.curtjrees.recipes.server

import com.curtjrees.recipes.sharedCore.ApiRecipesResponse
import com.curtjrees.recipes.sharedCore.Recipe
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    //Database setup
    Database.connect(System.getenv("JDBC_DATABASE_URL"), driver = "org.postgresql.Driver")

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Recipes)

//        DbRecipe.new {
//            name = "First DB Recipe"
//        }
        println("Recipes: ${DbRecipe.all().joinToString { it.name }}")
    }


    //Server setup
    val port = System.getenv("PORT")?.toInt() ?: 9090
    embeddedServer(factory = Netty, port = port) {
        install(ContentNegotiation) {
            json()
        }

        routing {

            get("/recipes") {
                val dbRecipes = transaction { DbRecipe.all().toList() }
                val apiRecipes = dbRecipes.map {
                    Recipe(it.id.value.toLong(), it.name)
                }

                val response = ApiRecipesResponse(
                    status = 200,
                    data = apiRecipes
                )
                call.respond(response)


//                val recipe = Recipe(0L, "First Recipe")
//                val response = ApiRecipesResponse(
//                    status = 200,
//                    data = listOf(recipe)
//                )
//                call.respond(response)
            }

        }
    }.start(wait = true)
}


object Recipes : IntIdTable() {
    val name = varchar("name", 255)
}

class DbRecipe(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DbRecipe>(Recipes)

    var name by Recipes.name
}