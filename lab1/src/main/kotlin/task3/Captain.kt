package task3

class Captain(val crewMember: CrewMember, val starShip: StarShip) {

    private val orders = mapOf(
        "Prepare for launch" to 20,
        "Engage hyperdrive" to 30,
        "Initiate landing sequence" to 40
    )

    fun giveOrder(orderName: String): Boolean {
        if (crewMember.getEffectiveCapacity() == 0) {
            return false // Капитан слишком пьян, чтобы отдавать приказы
        }

        val requiredCapacity = orders[orderName] ?: return false // Приказ не найден
        val availableCapacity = starShip.calculateCapacity()

        return availableCapacity >= requiredCapacity
    }
}