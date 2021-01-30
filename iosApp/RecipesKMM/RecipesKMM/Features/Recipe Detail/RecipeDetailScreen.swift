//
//  RecipeDetailScreen.swift
//  RecipesKMM
//
//  Created by Curtis Rees on 30/01/2021.
//

import SwiftUI
import URLImage
import sharedFrontend

struct RecipeDetailScreen: View {
    var recipeId: Int64
    @StateObject var viewModel = RecipeDetailViewModel(repository: RecipesRepository())
    var body: some View {
        
        ScrollView {
            VStack(alignment: .leading) {
                viewModel.recipe?.image_url.map { imageUrl in
                    URL(string: imageUrl).map { url in
                        URLImage(url: url) { image in
                            image.resizable().aspectRatio(contentMode: .fit)
                        }
                    }
                }
                
                
                Text(viewModel.recipe?.name ?? "").font(.title).padding()
                
                
                Text("Ingredients").font(.title2).padding(.leading)
                let ingrArray = (viewModel.recipe?.ingredients ?? "").components(separatedBy: "$$")
                
                ForEach(ingrArray, id: \.self) { ingredient in
                    Text(ingredient).font(.body).padding(.horizontal).padding(.top, 2)
                }
                
                Text("Steps").font(.title2).padding(.top).padding(.leading).padding(.bottom, 2)
                let stepsArray = (viewModel.recipe?.steps ?? "").components(separatedBy: "$$")
                ForEach(stepsArray.indices, id: \.self) { i in
                    Text("Step \(i+1)").font(.title3).padding(.horizontal)
                    Text("\(stepsArray[i])").font(.body).padding(.horizontal).padding(.top, 2).padding(.bottom, 30)
                }

                Spacer()
            }
        }
        .onAppear {
            self.viewModel.startObservingRecipe(recipeId: recipeId)
         }.onDisappear {
            self.viewModel.stopObservingRecipe()
         }.navigationBarTitleDisplayMode(.inline)
    }
    
}
