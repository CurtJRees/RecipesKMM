//
//  RecipeListScreen.swift
//  RecipesKMM
//
//  Created by Curtis Rees on 30/01/2021.
//

import SwiftUI
import sharedFrontend

struct RecipeListScreen: View {
    @StateObject var viewModel = RecipeListViewModel(repository: RecipesRepository())
    
    
    var body: some View {
        NavigationView {
            VStack {
                List(viewModel.recipes, id: \.name) { recipe in
                    NavigationLink(destination: RecipeDetailScreen(recipeId: recipe.id)) {
                        Text(recipe.name).padding(2.0)
                    }
                }
                .navigationBarTitle(Text("Recipes"))
                .onAppear {
                    self.viewModel.startObservingRecipeUpdates()
                 }.onDisappear {
                    self.viewModel.stopObservingRecipeUpdates()
                 }
            }
        }
    }
    
}
