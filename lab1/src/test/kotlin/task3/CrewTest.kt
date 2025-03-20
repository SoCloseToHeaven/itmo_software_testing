package task3

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CrewTest {

    @Test
    fun testParty() {
        val crewMembers = listOf(CrewMember(), CrewMember())
        val crew = Crew(crewMembers)
        crew.party()
        assertTrue(crewMembers.all { it.getEffectiveCapacity() < CrewMember.DEFAULT_EFFECTIVE_CAPACITY })
    }

    @Test
    fun testSoberUpAll() {
        val crewMembers = listOf(CrewMember(SobrietyStatus.DRUNK_AF), CrewMember(SobrietyStatus.MID))
        val crew = Crew(crewMembers)
        crew.soberUpAll()
        assertTrue(crewMembers.all { it.getEffectiveCapacity() > 0 })
    }
}