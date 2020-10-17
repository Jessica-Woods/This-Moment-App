package tech.jwoods.thismoment.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime


object Dialog {
    fun showZonedDatePickerDialog(context: Context, date: ZonedDateTime, callback: (ZonedDateTime) -> Unit) {
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

    fun showOptionsDialog(context: Context, title: String, options: Array<String>, callback: (Int) -> Unit) {
        // setup the alert builder

        // setup the alert builder
        val builder = AlertDialog.Builder(context).apply {
            setTitle(title)
        }

        builder.setItems(options) { _, which ->
            callback(which)
        }

        builder.create().show()
    }
}