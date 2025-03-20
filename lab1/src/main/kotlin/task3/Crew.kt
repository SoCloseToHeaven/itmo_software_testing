package task3

class Crew(
    val members: List<CrewMember>,
) {
    fun party() = members.forEach { it.drink() }
}