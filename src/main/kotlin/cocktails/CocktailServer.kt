package cocktails

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.dsl.*
import org.koin.ktor.ext.*
import org.koin.ktor.plugin.*

val appModule = module {
    single { CocktailDatabase() }
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    install(Koin) {
        modules(appModule)
    }

    routing {
        get("/health") {
            call.respondText("ok")
        }
        route("/api") {
            apiRoutes()
        }
    }
}

fun Route.apiRoutes() {
    val database: CocktailDatabase by inject()

    get("/zombie") {
        val rezept: Rezept = rezept("Pure White Zombie") {
            zutat("10cl Mineralwasser Medium")
            zutat("10cl Mineralwasser ohne Kohlensäure")
        }
        call.respondText(rezept.toString())
    }
    get("/cocktails") {
        val language = call.request.queryParameters["lang"]

        val cocktails = with(CocktailLoadContext(language)) {
            database.loadCocktails()
        }

        call.respond(cocktails)
    }
}

fun main() {
    val server = embeddedServer(
        Netty,
        port = 8080,
        module = Application::module
    )
    server.start(wait = true)
}
