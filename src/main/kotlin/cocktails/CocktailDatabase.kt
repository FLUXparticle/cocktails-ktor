package cocktails

class CocktailDatabase {

    fun loadCocktails(): List<Rezept> {
        // Cocktails laden
        val resource = this::class.java.getResource("/cocktails_de.txt")!!
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

}

fun main() {
    val database = CocktailDatabase()
    database.loadCocktails().forEach {
        println(it)
    }
}
