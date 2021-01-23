package com.curtjrees.recipes.desktop

import com.curtjrees.recipes.sharedCore.ApiRecipe
import com.curtjrees.recipes.sharedCore.ApiRecipesResponse
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class RecipesRepository {
    private val api = RecipesApi()
    suspend fun fetchRecipesFromApi() = api.fetchRecipes()
    suspend fun addNewRecipe(recipe: ApiRecipe) = api.addNewRecipe(recipe)
}

class RecipesApi {
//    private val baseUrl = "http://0.0.0.0:9090"
    private val baseUrl = "https://recipes-kmm.herokuapp.com/"
    private val nonStrictJson = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val client by lazy {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(nonStrictJson)
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }

    suspend fun fetchRecipes(): List<ApiRecipe> = client.get<ApiRecipesResponse>("$baseUrl/recipes").data.orEmpty()

    suspend fun addNewRecipe(recipe: ApiRecipe): ApiRecipe = client.post<ApiRecipe> {
        url("$baseUrl/recipes")
        contentType(ContentType.Application.Json)
        body = recipe
    }

}