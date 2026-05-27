package cocktails

sealed class CocktailResult {

    class Success(val rezept: Rezept) : CocktailResult()

    class Error(val message: String) : CocktailResult()

}
