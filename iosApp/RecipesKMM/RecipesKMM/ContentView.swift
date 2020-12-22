import SwiftUI
import sharedFrontend

//func greet() -> String {
//    return Greeting().greeting()
//}

struct ContentView: View {
    @StateObject var viewModel = RecipeListViewModel(repository: RecipesRepository())
    
    
    var body: some View {
//        Text("Blah")
        VStack {
            List(viewModel.recipes, id: \.name) { recipe in
                Text(recipe.name)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
