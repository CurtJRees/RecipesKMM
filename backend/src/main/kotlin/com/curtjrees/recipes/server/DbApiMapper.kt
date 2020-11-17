package com.curtjrees.recipes.server

import com.curtjrees.recipes.sharedCore.ApiRecipe

object DbApiMapper {

    fun map(dbItem: DbRecipe): ApiRecipe {
        return ApiRecipe(
            id = dbItem.id.value,
            name = dbItem.name,
            imageUrl = dbItem.imageUrl,
        )
    }

}