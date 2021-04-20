package tech.jwoods.thismoment.ui.album

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

// Solution Adapted from here: https://stackoverflow.com/questions/24618829/how-to-add-dividers-and-spaces-between-items-in-recyclerview?page=1&tab=votes#tab-top

class ShelfItemDecoration : ItemDecoration {
    private var divider: Drawable?

    // Default divider
    constructor(context: Context) {
        val styledAttributes: TypedArray = context.obtainStyledAttributes(ATTRS)
        divider = styledAttributes.getDrawable(0)
        styledAttributes.recycle()
    }

    // Custom divider
    constructor(context: Context, resId: Int) {
        divider = ContextCompat.getDrawable(context, resId)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom - (divider!!.intrinsicHeight / 2)
            val bottom = top + divider!!.intrinsicHeight
            divider!!.setBounds(left, top, right, bottom)
            divider!!.draw(c)
        }
    }

    companion object {
        private val ATTRS = intArrayOf(R.attr.listDivider)
    }
}