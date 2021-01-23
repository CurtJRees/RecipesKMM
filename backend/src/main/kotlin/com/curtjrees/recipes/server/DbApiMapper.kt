package com.curtjrees.recipes.server

import com.curtjrees.recipes.sharedCore.ApiRecipe

object DbApiMapper {

    fun map(dbItem: DbRecipe): ApiRecipe {
        return ApiRecipe(
            id = dbItem.id.value,
            name = dbItem.name,
            imageUrl = dbItem.imageUrl,
            steps = dbItem.steps?.split(DELIMITER).orEmpty(),
            ingredients = dbItem.ingredients?.split(DELIMITER).orEmpty()
        )
    }

    fun mapToDbPayload(apiItem: ApiRecipe): DbRecipePayload {
        return DbRecipePayload(
            name = apiItem.name,
            imageUrl = apiItem.imageUrl,
            steps = apiItem.steps.joinToString(DELIMITER),
            ingredients = apiItem.ingredients.joinToString(DELIMITER)
        )
    }

    private const val DELIMITER = "$$"

}