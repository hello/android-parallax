package `is`.hello.parallax

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.res.ResourcesCompat

data class InsightCard(val drawable: Drawable, val title: String) {
    constructor(resources: Resources,
                @DrawableRes drawableRes: Int,
                @StringRes titleRes: Int)
        : this(ResourcesCompat.getDrawable(resources, drawableRes, null),
               resources.getString(titleRes)) {}
}
