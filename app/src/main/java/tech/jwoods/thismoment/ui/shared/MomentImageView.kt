package tech.jwoods.thismoment.ui.shared

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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