package gradledsl.gradle

class Task(
    private val actions: Collection<() -> Unit>,
) {
    fun execute() {
        actions.forEach { it() }
    }
}
