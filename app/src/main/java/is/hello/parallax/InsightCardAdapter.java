package is.hello.parallax;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import is.hello.parallax.util.ParallaxRecyclerViewHolder;
import is.hello.parallax.util.Views;

public class InsightCardAdapter extends RecyclerView.Adapter<InsightCardAdapter.ViewHolder> {
    private static final float ASPECT_RATIO_SCALE = 0.5f /* 1:2 */;

    private final LayoutInflater inflater;
    private final int parallaxClip, parallaxMaxTranslation;
    private final InsightCard[] insightCards;

    public InsightCardAdapter(@NonNull Context context, @NonNull InsightCard[] insightCards) {
        this.inflater = LayoutInflater.from(context);
        this.parallaxClip = context.getResources().getDimensionPixelSize(R.dimen.parallax_clip);
        this.parallaxMaxTranslation = parallaxClip / 2;
        this.insightCards = insightCards;
    }

    @Override
    public int getItemCount() {
        return insightCards.length;
    }

    public InsightCard getItem(int position) {
        return insightCards[position];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final InsightCard insightCard = getItem(position);
        holder.image.setImageDrawable(insightCard.drawable);
        holder.title.setText(insightCard.title);
    }

    class ViewHolder extends ParallaxRecyclerViewHolder {
        final FrameLayout imageAperture; // Contains and clips the image
        final ImageView image;
        final TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageAperture = (FrameLayout) itemView.findViewById(R.id.item_card_image_aperture);
            this.image = (ImageView) itemView.findViewById(R.id.item_card_image);
            this.title = (TextView) itemView.findViewById(R.id.item_card_title);

            Views.runWhenLaidOut(itemView, /*this::onLaidOut*/ new Runnable() {
                @Override
                public void run() {
                    onLaidOut();
                }
            });
        }

        private void onLaidOut() {
            // We know our aspect ratio ahead of time due to the images being
            // made in-house, so we use that to our advantage to avoid having
            // to calculate the height of the image in a layout pass.
            final int width = image.getMeasuredWidth();
            final int height = Math.round(width * ASPECT_RATIO_SCALE);

            image.getLayoutParams().height = height;

            // The container is slightly smaller than the image
            // so that we have an aperture to move the image within.
            imageAperture.getLayoutParams().height = height - parallaxClip;

            // Ensure the image is centered, not sitting at the top of
            // the parallax container. See below for more discussion.
            image.setTranslationY(-parallaxMaxTranslation);

            imageAperture.requestLayout();
        }

        @Override
        public void setParallaxPercent(float percent) {
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
            image.setTranslationY(-parallaxMaxTranslation + (parallaxMaxTranslation * percent));
        }
    }
}
