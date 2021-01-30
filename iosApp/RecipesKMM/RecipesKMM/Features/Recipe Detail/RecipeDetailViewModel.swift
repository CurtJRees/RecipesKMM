//
//  RecipeDetailViewModel.swift
//  RecipesKMM
//
//  Created by Curtis Rees on 30/01/2021.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import sharedFrontend

class RecipeDetailViewModel: ObservableObject {

    @Published var recipe: Recipe?

    private let repository: RecipesRepository
    init(repository: RecipesRepository) {
        self.repository = repository
    }

    func startObservingRecipe(recipeId: Int64) {
        repository.startObservingRecipe(recipeId: recipeId, success: { data in
            self.recipe = data
        })
    }

    func stopObservingRecipe() {
        repository.stopObservingRecipe()
    }

}
