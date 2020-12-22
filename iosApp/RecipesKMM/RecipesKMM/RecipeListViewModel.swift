//
//  RecipeListViewModel.swift
//  RecipesKMM
//
//  Created by Curtis Rees on 21/12/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import sharedFrontend

class RecipeListViewModel: ObservableObject {

    @Published var recipes = [Recipe]()

    private let repository: RecipesRepository
    init(repository: RecipesRepository) {
        self.repository = repository
    }

    func startObservingRecipeUpdates() {
        repository.startObservingRecipeUpdates(success: { data in
            self.recipes = data
        })
    }

    func stopObservingRecipeUpdates() {
        repository.stopObservingRecipeUpdates()
    }

}
