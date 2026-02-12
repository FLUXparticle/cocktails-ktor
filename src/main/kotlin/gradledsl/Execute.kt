package gradledsl

import gradledsl.gradle.TasksBuilder.Companion.tasks

fun main() {
    println("=== Phase 2 ===")
    tasks.register("myTask") {
        doFirst {
            println("Anfang!")
        }
        doLast {
            println("Ende!")
        }
    }

    tasks.register("aTask") {
        doLast {
            println("Ende!")
        }
        doFirst {
            println("Anfang!")
        }
    }

    println("=== Phase 3 ===")
    tasks.execute()
}
