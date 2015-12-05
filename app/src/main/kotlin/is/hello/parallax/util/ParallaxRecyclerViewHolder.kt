package `is`.hello.parallax.util

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Extends the `RecyclerView.ViewHolder` to provide support for parallax effects.
 */
abstract class ParallaxRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * Sets the parallax offset for the view holder. This method will be called many times
     * when the user scrolls the containing recycler view, so it should be as fast as possible.

     * @param percent The offset percentage, a value between `-1.0` and `1.0` inclusive.
     */
    abstract fun setParallaxPercent(percent: Float)
}
