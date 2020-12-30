package com.curtjrees.recipes.sharedCore

import kotlinx.serialization.Serializable

@Serializable
data class ApiRecipeResponse (
    val status: Int,
    val data: ApiRecipe?
)

@Serializable
data class ApiRecipesResponse (
    val status: Int,
    val data: List<ApiRecipe>?
)

@Serializable
data class ApiRecipe(
    val id: Long,
    val name: String,
    val imageUrl: String?,
    val steps: List<String>
)