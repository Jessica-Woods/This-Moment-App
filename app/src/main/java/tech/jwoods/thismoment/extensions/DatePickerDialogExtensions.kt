package tech.jwoods.thismoment.extensions

import android.app.DatePickerDialog
import android.content.Context
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

object DatePickerDialogExtensions {
    fun show(context: Context, date: ZonedDateTime, callback: (ZonedDateTime) -> Unit) {
        val datePickerCallback = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val zonedDateTime = ZonedDateTime.of(
                LocalDate.of(year, month+1, dayOfMonth),
                LocalTime.of(0, 0, 0),
                ZoneId.systemDefault()
            )

            callback(zonedDateTime)
        }

        DatePickerDialog(
            context, datePickerCallback, date.year, date.monthValue-1, date.dayOfMonth
        ).show()
    }
}