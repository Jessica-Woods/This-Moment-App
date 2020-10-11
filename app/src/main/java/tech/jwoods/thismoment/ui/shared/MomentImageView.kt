package tech.jwoods.thismoment.ui.shared

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Moment

class MomentImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater
            .from(context)
            .inflate(R.layout.view_moment_image, this, true)
    }

    fun setMoment(moment: Moment) {
        // TODO: Set image from moment
    }
}