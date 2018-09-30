package com.example.hendratay.whatheweather.presentation.view.utils

import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView

class RecyclerViewSnapHelper: PagerSnapHelper() {

    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager?, velocityX: Int, velocityY: Int): Int {
        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) return RecyclerView.NO_POSITION
        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        val currentPosition = layoutManager.getPosition(currentView)
        var targetPosition = -1
        if (layoutManager.canScrollHorizontally()) {
            targetPosition = if (velocityX < 0) currentPosition - 1 else currentPosition + 1
        }
        if (layoutManager.canScrollVertically()) {
            targetPosition = if (velocityY < 0) currentPosition - 1  else currentPosition + 1
        }
        targetPosition = Math.min(layoutManager.itemCount - 1, Math.max(targetPosition, 0))
        return targetPosition
    }

}
