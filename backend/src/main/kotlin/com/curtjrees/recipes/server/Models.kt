package com.curtjrees.recipes.server

data class DbRecipePayload(
    val name: String,
    val imageUrl: String?,
    val steps: String?,
    val ingredients: String,
)