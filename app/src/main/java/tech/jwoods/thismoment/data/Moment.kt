package tech.jwoods.thismoment.data

import java.time.LocalDate

data class Moment (
    val title: String,
    val description: String,
    val date: LocalDate

    // TODO Starred + Image
)