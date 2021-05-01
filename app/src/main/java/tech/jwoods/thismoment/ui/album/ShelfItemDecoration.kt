package tech.jwoods.thismoment.ui.album

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

// Solution Adapted from: https://stackoverflow.com/questions/24618829/how-to-add-dividers-and-spaces-between-items-in-recyclerview?page=1&tab=votes#tab-top

class ShelfItemDecoration : ItemDecoration {
    private var divider: Drawable?

    // Default divider
    constructor(context: Context) {
        val styledAttributes: TypedArray = context.obtainStyledAttributes(intArrayOf(R.attr.listDivider))
        divider = styledAttributes.getDrawable(0)
        styledAttributes.recycle()
    }

    // Custom divider
    constructor(context: Context, resId: Int) {
        divider = ContextCompat.getDrawable(context, resId)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView) {
        divider?.let { divider ->
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            for (child in parent.children) {
                val top = child.bottom - (divider.intrinsicHeight / 2)
                val bottom = top + divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
        }
    }
}