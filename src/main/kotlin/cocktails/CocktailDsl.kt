package cocktails

data class Zutat(val name: String)

data class Rezept(val name: String, val zutaten: List<Zutat>)

class RezeptScope {
    val zutaten = mutableListOf<Zutat>()
    fun zutat(name: String) {
        zutaten.add(Zutat(name))
    }
}

fun rezept(name: String, block: RezeptScope.() -> Unit): Rezept {
    val scope = RezeptScope()
    block(scope)
    return Rezept(name, scope.zutaten)
}

fun main() {
    val rezept: Rezept = rezept("Pure White Zombie") {
        zutat("10cl Mineralwasser Medium")
        zutat("10cl Mineralwasser ohne Kohlensäure")
    }

    println(rezept)
}
