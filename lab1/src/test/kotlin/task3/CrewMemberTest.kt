package task3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CrewMemberTest {

    @Test
    fun testDrink() {
        val crewMember = CrewMember()
        crewMember.drink()
        assertEquals(5, crewMember.getEffectiveCapacity()) // MID состояние
        crewMember.drink()
        assertEquals(0, crewMember.getEffectiveCapacity())
    }

    @Test
    fun testSoberUp() {
        val crewMember = CrewMember(SobrietyStatus.DRUNK_AF)
        crewMember.soberUp()
        assertEquals(5, crewMember.getEffectiveCapacity()) // MID состояние
        crewMember.soberUp()
        assertEquals(10, crewMember.getEffectiveCapacity()) // MID состояние
    }
}