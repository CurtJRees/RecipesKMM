
package com.curtjrees.recipes.server

import com.curtjrees.recipes.sharedCore.Recipe
import com.curtjrees.recipes.sharedCore.ApiRecipesResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 9090

    embeddedServer(factory = Netty, port = port) {
        install(ContentNegotiation) {
            json()
        }

        routing {

            get("/recipes") {
                val recipe = Recipe(0L, "First Recipe")
                val response = ApiRecipesResponse(
                    status = 200,
                    data = listOf(recipe)
                )
                call.respond(response)
            }

        }
    }.start(wait = true)
}