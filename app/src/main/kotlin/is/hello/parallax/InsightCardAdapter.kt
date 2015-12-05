package `is`.hello.parallax

import `is`.hello.parallax.util.ParallaxRecyclerViewHolder
import `is`.hello.parallax.util.afterLaidOut
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

internal class InsightCardAdapter(context: Context, private val insightCards: Array<InsightCard>) : RecyclerView.Adapter<InsightCardAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private val parallaxClip: Int
    private val parallaxMaxTranslation: Int

    init {
        this.inflater = LayoutInflater.from(context)
        this.parallaxClip = context.resources.getDimensionPixelSize(R.dimen.parallax_clip)
        this.parallaxMaxTranslation = parallaxClip / 2
    }

    override fun getItemCount(): Int {
        return insightCards.size
    }

    fun getItem(position: Int): InsightCard {
        return insightCards[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val insightCard = getItem(position)
        holder.image.setImageDrawable(insightCard.drawable)
        holder.title.text = insightCard.title
    }

    internal inner class ViewHolder(itemView: View) : ParallaxRecyclerViewHolder(itemView) {
        val imageAperture: FrameLayout // Contains and clips the image
        val image: ImageView
        val title: TextView

        init {
            this.imageAperture = itemView.findViewById(R.id.item_card_image_aperture) as FrameLayout
            this.image = itemView.findViewById(R.id.item_card_image) as ImageView
            this.title = itemView.findViewById(R.id.item_card_title) as TextView

            itemView.afterLaidOut { onLaidOut() }
        }

        private fun onLaidOut() {
            // We know our aspect ratio ahead of time due to the images being
            // made in-house, so we use that to our advantage to avoid having
            // to calculate the height of the image in a layout pass.
            val width = image.measuredWidth
            val height = Math.round(width * ASPECT_RATIO_SCALE)

            image.layoutParams.height = height

            // The container is slightly smaller than the image
            // so that we have an aperture to move the image within.
            imageAperture.layoutParams.height = height - parallaxClip

            // Ensure the image is centered, not sitting at the top of
            // the parallax container. See below for more discussion.
            image.translationY = (-parallaxMaxTranslation).toFloat()

            imageAperture.requestLayout()
        }

        override fun setParallaxPercent(percent: Float) {
            // Conceptually, the image's natural position is in the center of the parallax
            // container. However, FrameLayout does not layout views that are larger than
            // itself in the center even if told to, so we have to manually adjust our
            // parallax translation to ensure the image moves relative to the center of
            // the container and not the top of the container (which would result in the
            // image having unwanted whitespace; remove -parallaxMaxTranslation to see.)
            //
            // A better implementation of this parallax effect would probably be a custom
            // view that draws an image in its center, and adjusts that center value based
            // on the parallax percentage we get from our scroll listener.
            image.translationY = -parallaxMaxTranslation + (parallaxMaxTranslation * percent)
        }
    }

    companion object {
        private val ASPECT_RATIO_SCALE = 0.5f/* 1:2 */
    }
}
