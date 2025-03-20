package task3

class CrewMember(
    private var sobrietyStatus: SobrietyStatus = SobrietyStatus.SOBER,
) {
    companion object {
        const val DEFAULT_EFFECTIVE_CAPACITY = 10
    }

    fun drink() {
        sobrietyStatus = next(sobrietyStatus)
    }

    fun soberUp() {
        sobrietyStatus = prev(sobrietyStatus)
    }

    fun getEffectiveCapacity() = when (sobrietyStatus) {
        SobrietyStatus.SOBER -> DEFAULT_EFFECTIVE_CAPACITY
        SobrietyStatus.MID -> DEFAULT_EFFECTIVE_CAPACITY / 2
        SobrietyStatus.DRUNK_AF -> 0
    }

    private fun isSober() = sobrietyStatus == SobrietyStatus.SOBER
}