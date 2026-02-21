package cocktails

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.module() {
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
    get("/zombie") {
        val rezept: Rezept = rezept("Pure White Zombie") {
            zutat("10cl Mineralwasser Medium")
            zutat("10cl Mineralwasser ohne Kohlensäure")
        }
        call.respondText(rezept.toString())
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
