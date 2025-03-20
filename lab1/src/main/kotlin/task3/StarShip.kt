package task3

class StarShip(private val crew: Crew) {

    fun calculateCapacity(): Int {
        return crew.members.sumOf { it.getEffectiveCapacity() }
    }

    fun canExecuteOrder(requiredCapacity: Int): Boolean {
        return calculateCapacity() >= requiredCapacity
    }
}