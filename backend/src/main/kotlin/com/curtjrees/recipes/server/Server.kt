package com.curtjrees.recipes.server

import com.curtjrees.recipes.sharedCore.ApiRecipe
import com.curtjrees.recipes.sharedCore.ApiRecipeResponse
import com.curtjrees.recipes.sharedCore.ApiRecipesResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.receiveOrNull
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.pipeline.*
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    setupDatabase()
    startServer()
}

fun Application.main() {
    setupDatabase()
    startServer()
}

private fun startServer() {
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

            get("/recipes/{recipeId}") {
                val recipeId = call.parameters["recipeId"]?.toLongOrNull() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Missing or invalid recipeId")
                    return@get
                }

                val dbRecipe = transaction { DbRecipe.findById(recipeId) } ?: run {
                    call.respond(HttpStatusCode.NotFound, "No recipe with id $recipeId found")
                    return@get
                }

                val apiRecipe = DbApiMapper.map(dbRecipe)

                val response = ApiRecipeResponse(
                    status = 200,
                    data = apiRecipe
                )
                call.respond(response)
            }

            post("/recipes") {
                //TODO: Look into using JWT to secure post endpoint

                val newRecipe = call.receiveOrNull<ApiRecipe>() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Missing or invalid Recipe payload")
                    return@post
                }
                val dbRecipePayload = DbApiMapper.mapToDbPayload(newRecipe)

                //TODO: Look into error handling for this insert
                val newDbItem = transaction {
                    DbRecipe.new {
                        name = dbRecipePayload.name
                        imageUrl = dbRecipePayload.imageUrl
                        steps = dbRecipePayload.steps
                        ingredients = dbRecipePayload.ingredients
                    }
                }

                val newApiRecipe = newRecipe.copy(id = newDbItem.id.value)
                val response = ApiRecipeResponse(
                    status = 200,
                    data = newApiRecipe
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
    }
}


object Recipes : LongIdTable() {
    val name = varchar("name", 255)
    val imageUrl = varchar("image_url", 255).nullable()
    val steps = text("steps").nullable()
    val ingredients = text("ingredients").nullable()
}

class DbRecipe(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<DbRecipe>(Recipes)

    var name by Recipes.name
    var imageUrl by Recipes.imageUrl
    var steps by Recipes.steps
    var ingredients by Recipes.ingredients
}