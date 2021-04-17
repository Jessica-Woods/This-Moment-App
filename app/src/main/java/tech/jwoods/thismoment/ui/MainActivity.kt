package tech.jwoods.thismoment.ui

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.GestureDetectorCompat
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.extensions.hideKeyboard

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector = GestureDetectorCompat(this, HideEditTextOnTapListener(this))

        fragment.findNavController().addOnDestinationChangedListener { _, _, _ ->
            currentFocus?.let { view -> view.hideKeyboard() }
        }
    }

    override fun dispatchTouchEvent(e: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(e)
        return super.dispatchTouchEvent(e)
    }

    private class HideEditTextOnTapListener(val activity: Activity) : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            activity.currentFocus?.let { focusedView ->
                if (focusedView is EditText) {
                    var focusRect = Rect()
                    focusedView.getGlobalVisibleRect(focusRect)

                    val tapX = e.rawX.toInt()
                    val tapY = e.rawY.toInt()
                    if (!focusRect.contains(tapX, tapY)) {
                        focusedView.clearFocus()
                        focusedView.hideKeyboard()
                    }
                }
            }

            return true
        }
    }
}