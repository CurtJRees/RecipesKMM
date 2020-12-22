import SwiftUI
import sharedFrontend

struct ContentView: View {
    @StateObject var viewModel = RecipeListViewModel(repository: RecipesRepository())
    
    
    var body: some View {
        NavigationView {
            VStack {
                List(viewModel.recipes, id: \.name) { recipe in
                    NavigationLink(destination: RecipeDetailsView(viewModel: self.viewModel, recipe: recipe)) {
                        Text(recipe.name)
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

struct RecipeDetailsView: View {
    var viewModel: RecipeListViewModel
    var recipe: Recipe
    
    var body: some View {
        VStack {
            Text(recipe.name).font(.title)
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
