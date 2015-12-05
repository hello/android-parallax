package `is`.hello.parallax.util

import `is`.hello.parallax.R
import android.content.res.Resources
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class CardItemDecoration(resources: Resources) : RecyclerView.ItemDecoration() {
    private val outerHorizontal: Int
    private val outerVertical: Int
    private val inter: Int

    init {
        this.outerHorizontal = resources.getDimensionPixelSize(R.dimen.gap_card_horizontal)
        this.outerVertical = resources.getDimensionPixelSize(R.dimen.gap_card_vertical)
        this.inter = resources.getDimensionPixelSize(R.dimen.gap_card_inter)
    }

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State?) {
        val lastPosition = (parent.adapter.itemCount - 1)
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.top = outerVertical
        }

        if (position == lastPosition) {
            outRect.bottom = outerVertical
        } else {
            outRect.bottom = inter
        }

        outRect.left = outerHorizontal
        outRect.right = outerHorizontal
    }
}
