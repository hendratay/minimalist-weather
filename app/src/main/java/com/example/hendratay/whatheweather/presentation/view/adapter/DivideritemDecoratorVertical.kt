package com.example.hendratay.whatheweather.presentation.view.adapter

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View


class DivideritemDecoratorVertical(val mDivider: Drawable): RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas?, parent: RecyclerView, state: RecyclerView.State?) {
        val dividerTop = parent.paddingTop
        val dividerBottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount

        for (i in 0..childCount - 5) {
            val child: View = parent.getChildAt(i)
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams

            val dividerLeft = child.right + params.rightMargin
            val dividerRight = dividerLeft + mDivider.intrinsicWidth

            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            mDivider.draw(c)
        }

    }
}
