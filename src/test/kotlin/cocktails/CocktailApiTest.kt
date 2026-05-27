package cocktails

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@Serializable
data class Cocktail(
    val id: Int,
    val name: String,
    val instructions: List<Instruction> = emptyList()
)

@Serializable
data class Instruction(
    val amount: Int,
    val ingredient: Ingredient
)

@Serializable
data class Ingredient(
    val id: Int,
    val name: String
)

class CocktailApiTest {

    private val baseUrl = "https://cocktails.fluxparticle.com/api"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    @Test
    fun `summiert Milch-Mengen in Milk-Cocktails`(): Unit = runTest {
        val cocktails: List<Cocktail> = getJson("$baseUrl/cocktails")

        val milkCocktails = cocktails
            .filter { it.name.contains("Milk", ignoreCase = true) }

        assertEquals(3, milkCocktails.size)

        val details = milkCocktails
            .map { getJson<Cocktail>("$baseUrl/cocktails/${it.id}") }

        val amount = details
            .flatMap { it.instructions }
            .filter { it.ingredient.name == "Milch" }
            .sumOf { it.amount }

        assertEquals(38, amount)
    }

    @Test
    fun `summiert Milch-Mengen mit parallelen Detailabfragen`(): Unit = runTest {
        val cocktails: List<Cocktail> =
            getJson("$baseUrl/cocktails")

        val milkCocktails = cocktails
            .filter { it.name.contains("Milk", ignoreCase = true) }

        assertEquals(3, milkCocktails.size)

        val details = milkCocktails
            .map { cocktail ->
                async {
                    getJson<Cocktail>("$baseUrl/cocktails/${cocktail.id}")
                }
            }
            .awaitAll()

        val amount = details
            .flatMap { it.instructions }
            .filter { it.ingredient.name == "Milch" }
            .sumOf { it.amount }

        assertEquals(38, amount)
    }

    private suspend inline fun <reified T> getJson(url: String): T {
        return client.get(url).body()
    }

}
