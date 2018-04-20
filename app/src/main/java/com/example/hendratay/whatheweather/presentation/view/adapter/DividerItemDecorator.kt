package com.example.hendratay.whatheweather.presentation.view.adapter

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

class DividerItemDecorator(val mDivider: Drawable): RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas?, parent: RecyclerView, state: RecyclerView.State?) {
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight

        val childCount = parent.childCount

        // Why 5 ? : because gridlayoutmanager span 3 + 2
        // if using 'until' only 4
        for (i in 0..childCount - 5) {
            val child: View = parent.getChildAt(i)
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + mDivider.intrinsicHeight

            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            mDivider.draw(c)
        }

    }
}