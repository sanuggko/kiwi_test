package ua.sng.kiwitest.utils;

import android.content.Context;

/**
 * Created by Lampa on 20.03.2017.
 */

public class DpPxUtils {

    public static int pxToDp(final Context context, final float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public static int dpToPx(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

}
