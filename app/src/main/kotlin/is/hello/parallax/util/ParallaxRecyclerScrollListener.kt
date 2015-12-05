package `is`.hello.parallax.util

import android.support.v7.widget.RecyclerView

/**
 * Provides parallax offset calculation for subclasses of [ParallaxRecyclerViewHolder].
 * Assumes that all view holders in the recycler view are subclasses.
 */
class ParallaxRecyclerScrollListener : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        val recyclerCenter = recyclerView!!.measuredHeight / 2
        for (childView in recyclerView) {
            val childViewCenter = (childView.top + childView.bottom) / 2
            val viewHolder = recyclerView.getChildViewHolder(childView) as ParallaxRecyclerViewHolder

            // For the purpose of calculating a parallax offset, the recycler view is split
            // into two segments: the area above its center, and the area below. The parallax
            // percentage is then calculated as the distance from this center relative to the
            // view holder.
            //
            // +------- (top, -1.0f)
            // |
            // | <--- (top mid-point, -0.5f)
            // |
            // +------- (center, 0.0f)
            // |
            // | <--- (bottom mid-point)
            // |
            // +------- (bottom, 1.0f)
            val percent = (childViewCenter - recyclerCenter) / recyclerCenter.toFloat()
            viewHolder.setParallaxPercent(percent)
        }
    }
}
