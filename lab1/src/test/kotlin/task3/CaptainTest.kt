package task3

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CaptainTest {

    @Test
    fun `test give order success`() {
        val crewMembers = List(4) { CrewMember() } // 4 члена экипажа, каждый с capacity = 10
        val crew = Crew(crewMembers)
        val starShip = StarShip(crew)
        val captain = Captain(CrewMember(), starShip)

        assertTrue(captain.giveOrder("Prepare for launch")) // Требуется 20, доступно 40
    }

    @Test
    fun `test fail due to capacity`() {
        val crewMembers = List(2) { CrewMember() } // 2 члена экипажа, каждый с capacity = 10
        val crew = Crew(crewMembers)
        val starShip = StarShip(crew)
        val captain = Captain(CrewMember(), starShip)

        assertFalse(captain.giveOrder("Engage hyperdrive")) // Требуется 30, доступно 20
    }

    @Test
    fun `test fail due to drunk captain`() {
        val crewMembers = List(4) { CrewMember() } // 4 члена экипажа, каждый с capacity = 10
        val crew = Crew(crewMembers)
        val starShip = StarShip(crew)
        val drunkCaptain = Captain(CrewMember(SobrietyStatus.DRUNK_AF), starShip)

        assertFalse(drunkCaptain.giveOrder("Prepare for launch")) // Капитан пьян
    }

    @Test
    fun `test give invalid order`() {
        val crewMembers = List(4) { CrewMember() } // 4 члена экипажа, каждый с capacity = 10
        val crew = Crew(crewMembers)
        val starShip = StarShip(crew)
        val captain = Captain(CrewMember(), starShip)

        assertFalse(captain.giveOrder("Unknown order")) // Неизвестный приказ
    }
}