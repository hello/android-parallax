package `is`.hello.parallax.util

import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

fun View.afterLaidOut(f: () -> Unit) {
    if (ViewCompat.isLaidOut(this)) {
        f()
    } else if (isInLayout) {
        post(f)
    } else {
        viewTreeObserver.addOnGlobalLayoutListener(OneShotLayoutListener(this, f))
    }
}

operator fun ViewGroup.iterator(): Iterator<View> {
    return object : Iterator<View> {
        val count = childCount
        var index = 0

        override fun hasNext(): Boolean {
            return index < count
        }

        override fun next(): View {
            return getChildAt(index++)
        }
    }
}

private class OneShotLayoutListener(val observee: View,
                                    val f: () -> Unit) : ViewTreeObserver.OnGlobalLayoutListener {
    override fun onGlobalLayout() {
        f()
        observee.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }
}
