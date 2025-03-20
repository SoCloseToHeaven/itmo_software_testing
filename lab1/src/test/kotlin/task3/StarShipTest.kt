package task3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StarShipTest {

    @Test
    fun testCalculateCapacity() {
        val crewMembers = listOf(CrewMember(), CrewMember())
        val crew = Crew(crewMembers)
        val starShip = StarShip(crew)
        assertEquals(20, starShip.calculateCapacity())
    }

    @Test
    fun testCanExecuteOrder() {
        val crewMembers = listOf(CrewMember(), CrewMember())
        val crew = Crew(crewMembers)
        val starShip = StarShip(crew)
        assertTrue(starShip.canExecuteOrder(20)) // Достаточно capacity
        assertFalse(starShip.canExecuteOrder(30)) // Недостаточно capacity
    }
}