package tech.jwoods.thismoment.ui.shared

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_moment_image.view.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Moment
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

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

    fun animatePolaroidFadeIn() {
        momentPhotoFadeOverlay.visibility = View.VISIBLE

        momentPhotoFadeOverlay.animate()
            .alpha(0.0f)
            .setDuration(500)
            .withEndAction {
                momentPhotoFadeOverlay.visibility = View.GONE
            }
    }

    fun setDate(date: ZonedDateTime?) {
        if (date != null) {
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            momentDate.text = date.format(formatter)
            momentDate.visibility = View.VISIBLE
        } else {
            momentDate.visibility = View.GONE
        }
    }

    fun setPhoto(uri: Uri?) {
        Glide
            .with(context)
            .load(uri)
            .into(momentPhoto)
    }

    fun setOnMomentPhotoClickListener(callback: () -> Unit) {
        momentPhoto.setOnClickListener { callback() }
    }

    fun setOnMomentDateClickListener(callback: () -> Unit) {
        momentDate.setOnClickListener { callback() }
    }

    fun setGoldFrame(moment: Moment) {
        val rand = (moment.hashCode().absoluteValue % 10) - 5

        if (moment.starred) {
            goldFrame.rotation = rand.toFloat()
            goldFrame.visibility = View.VISIBLE
        } else {
            goldFrame.visibility = View.GONE
        }
    }
}