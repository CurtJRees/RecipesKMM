package com.curtjrees.recipes.sharedCore

import kotlinx.serialization.Serializable

data class CoreModel(
    val name: String
)

@Serializable
data class ApiRecipesResponse (
    val status: Int,
    val data: List<Recipe>
)

@Serializable
data class Recipe(
    val id: Long,
    val name: String
)