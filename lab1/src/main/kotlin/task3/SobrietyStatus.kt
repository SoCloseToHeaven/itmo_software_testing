package task3

enum class SobrietyStatus {
    SOBER,
    MID,
    DRUNK_AF
}

fun next(status: SobrietyStatus) = when(status) {
    SobrietyStatus.SOBER -> SobrietyStatus.MID
    SobrietyStatus.MID -> SobrietyStatus.DRUNK_AF
    SobrietyStatus.DRUNK_AF -> SobrietyStatus.DRUNK_AF
}

fun prev(status: SobrietyStatus) = when(status) {
    SobrietyStatus.SOBER -> SobrietyStatus.SOBER
    SobrietyStatus.MID -> SobrietyStatus.SOBER
    SobrietyStatus.DRUNK_AF -> SobrietyStatus.MID
}