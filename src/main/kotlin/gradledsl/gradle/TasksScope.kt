package gradledsl.gradle

class TasksScope {
    private val actions = ArrayDeque<() -> Unit>()

    @Suppress("UNUSED_PARAMETER")
    fun dependsOn(task: Task) {
        // Kept as a DSL hook; this tiny example does not model task ordering.
    }

    internal fun build(): Task {
        return Task(actions)
    }

    fun doLast(action: () -> Unit) {
        actions.addLast(action)
    }

    fun doFirst(action: () -> Unit) {
        actions.addFirst(action)
    }
}
