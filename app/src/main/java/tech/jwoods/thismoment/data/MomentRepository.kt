package tech.jwoods.thismoment.data

import java.time.LocalDate

class MomentRepository {
    fun getMoments():List<Moment> {
        return listOf(
            Moment("Moment 1","This is a nice moment", LocalDate.now()),
            Moment("Moment 2","This one is ok", LocalDate.now())
        )
    }
}