package task3

class StarShip(val crew: Crew) {

    fun calculateCapacity() = crew.members
        .stream()
        .map { it.getEffectiveCapacity() }
        .reduce(Int::plus)
}