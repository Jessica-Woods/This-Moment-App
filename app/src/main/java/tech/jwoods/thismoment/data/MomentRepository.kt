package tech.jwoods.thismoment.data

import java.time.LocalDate

class MomentRepository {
    fun getMoments():List<Moment> {
        return listOf(
            Moment("Moment 1","This is a nice moment", LocalDate.now()),
            Moment("Pumpkin Carving Pumpkin Carving","This one is ok", LocalDate.now()),
            Moment("Moment 3","This is a nice moment", LocalDate.now()),
            Moment("Moment 4","This one is ok", LocalDate.now()),
            Moment("Pumpkin Carving at the foots","This one is ok", LocalDate.now()),
            Moment("Pumpkin Carving at the doots","This one is ok", LocalDate.now())
        )
    }
}