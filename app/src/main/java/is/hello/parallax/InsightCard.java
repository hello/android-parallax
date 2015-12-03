package is.hello.parallax;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;

public class InsightCard {
    public final Drawable drawable;
    public final String title;

    public InsightCard(Drawable drawable, String title) {
        this.drawable = drawable;
        this.title = title;
    }

    public InsightCard(@NonNull Resources resources,
                       @DrawableRes int drawableRes,
                       @StringRes int titleRes) {
        this(ResourcesCompat.getDrawable(resources, drawableRes, null),
             resources.getString(titleRes));
    }

    @Override
    public String toString() {
        return "InsightCard{" +
                "drawable=" + drawable +
                ", title='" + title + '\'' +
                '}';
    }
}
