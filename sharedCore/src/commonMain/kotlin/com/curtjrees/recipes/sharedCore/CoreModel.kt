package com.curtjrees.recipes.sharedCore

import kotlinx.serialization.Serializable

data class CoreModel(
    val name: String
)

@Serializable
data class ApiRecipeResponse (
    val status: Int,
    val data: ApiRecipe
)

@Serializable
data class ApiRecipesResponse (
    val status: Int,
    val data: List<ApiRecipe>
)

@Serializable
data class ApiRecipe(
    val id: Long,
    val name: String,
    val imageUrl: String?
)