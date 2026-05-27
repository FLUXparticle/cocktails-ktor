package cocktails

class CocktailLoadContext(val language: String?)

class CocktailDatabase {

    context(CocktailLoadContext)
    fun loadCocktails(): List<Rezept> {
        // Cocktails laden
        val filename = "/cocktails_${language ?: "de"}.txt"
        val resource = this::class.java.getResource(filename)!!
        println("resource = $resource")

        val rezepte = mutableListOf<Rezept>()

        resource.openStream().bufferedReader().use { reader ->
            while (true) {
                val name = reader.readLine() ?: return@use

                rezept(name) {
                    var line: String?
                    while (true) {
                        line = reader.readLine()
                        if (line.isEmpty()) {
                            break
                        }
                        // Zutat hinzufügen
                        zutat(line)
                    }
                }.let { rezepte.add(it) }
            }
        }

        return rezepte
    }

    context(CocktailLoadContext)
    fun loadCocktail(id: Int): CocktailResult {
        val cocktails = loadCocktails()
        return cocktails.getOrNull(id - 1)
            ?.let { CocktailResult.Success(it) }
            ?: CocktailResult.Error("Cocktail with id=$id not found")
    }

}

fun main() {
    val database = CocktailDatabase()
    with(CocktailLoadContext("de")) {
        database.loadCocktails().forEach {
            println(it)
        }
    }
}
