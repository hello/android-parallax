package is.hello.parallax.util;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;

public final class Views {
    /**
     * Executes a runnable when a given view has been laid out.
     * <p>
     * If the view is laid out at call time, the runnable will be immediately executed.
     * If the view is in the process of laying out, the runnable will be executed on the
     * next looper cycle. Otherwise, a layout listener is attached to the view and the
     * runnable will be executed when the listener receives a callback.
     *
     * @param view      The view that needs to be laid out before the runnable can be run.
     * @param runnable  The runnable to execute when the view is laid out.
     */
    public static void runWhenLaidOut(@NonNull final View view, @NonNull final Runnable runnable) {
        if (ViewCompat.isLaidOut(view)) {
            runnable.run();
        } else if (view.isInLayout()) {
            view.post(runnable);
        } else {
            final ViewTreeObserver.OnGlobalLayoutListener listener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    runnable.run();
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            };
            view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
        }
    }
}
