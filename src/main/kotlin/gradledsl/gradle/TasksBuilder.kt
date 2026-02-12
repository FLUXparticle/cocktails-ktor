package gradledsl.gradle

import java.util.*

class TasksBuilder private constructor() {

    private val tasksByName = TreeMap<String, Task>()

    fun register(name: String, block: TasksScope.() -> Unit): Task {
        val task = TasksScope()
            .apply(block)
            .build()

        tasksByName[name] = task

        return task
    }

    fun execute() {
        for ((name, task) in tasksByName) {
            println("$name:")
            task.execute()
        }
    }

    companion object {
        val tasks: TasksBuilder = TasksBuilder()
    }
}
