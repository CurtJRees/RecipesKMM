package com.curtjrees.recipes.sharedFrontend

import com.curtjrees.recipes.sharedCore.ApiRecipe
import com.curtjrees.recipes.sharedCore.ApiRecipeResponse
import com.curtjrees.recipes.sharedCore.ApiRecipesResponse
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneNotNull
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class RecipesRepository {

    private val coroutineScope: CoroutineScope = MainScope()
    private val api = RecipesApi()
    private val db = createDb()

    suspend fun fetchRecipesFromApi() = api.fetchRecipes()
    suspend fun fetchRecipeFromApi(recipeId: Long) = api.fetchRecipe(recipeId)

    fun getRecipe(recipeId: Long): Flow<Recipe?> {
        updateDbFromApi(recipeId)
        return db?.recipesQueries?.selectById(recipeId)?.asFlow()?.mapToOneNotNull()!!
    }

    fun getRecipes(): Flow<List<Recipe>> {
        updateDbFromApi()
        return db?.recipesQueries?.selectAll()?.asFlow()?.mapToList()!!
    }

    fun updateDbFromApi(recipeId: Long) {
        coroutineScope.launch(Dispatchers.Default) {
            val recipe = fetchRecipeFromApi(recipeId)
            db?.recipesQueries?.insertItem(recipe.id, recipe.name, recipe.imageUrl)
        }
    }

    fun updateDbFromApi() {
        coroutineScope.launch(Dispatchers.Default) {
            val apiRecipes = fetchRecipesFromApi()

            //Lazy approach to clear db and insert new data, should be improved
            apiRecipes.forEach {
                db?.recipesQueries?.insertItem(it.id, it.name, it.imageUrl)
            }
        }
    }

}

class RecipesApi {
    //    private val baseUrl = "http://10.0.2.2:9090"
    private val baseUrl = "https://recipes-kmm.herokuapp.com"
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

    suspend fun fetchRecipes(): List<ApiRecipe> = client.get<ApiRecipesResponse>("$baseUrl/recipes").data

    suspend fun fetchRecipe(recipeId: Long): ApiRecipe = client.get<ApiRecipeResponse>("$baseUrl/recipes/$recipeId").data

}