package tech.jwoods.thismoment.extensions

import android.widget.EditText

// Ellipsize doesn't work on EditText if the EditText is editable
//
// This function works around this limitation by making the
// EditText uneditable right up until the user taps it. And then
// making it uneditable after the user taps away.
//
// Assumes that `android:inputType` and `android:ellipsize` are set.
fun EditText.ellipsizeWorkaround() {
    val originalKeyListener = keyListener
    val originalEllipsize = ellipsize

    keyListener = null

    setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            ellipsize = null
            keyListener = originalKeyListener
        } else {
            ellipsize = originalEllipsize
            keyListener = null
        }
    }
}