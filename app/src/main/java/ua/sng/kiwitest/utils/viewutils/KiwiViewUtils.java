package ua.sng.kiwitest.utils.viewutils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import ua.sng.kiwitest.R;

/**
 * Created by sanug on 02.12.2017.
 */

public class KiwiViewUtils {

    public static MaterialDialog generateProgressDialog(Context context){
        return new MaterialDialog.Builder(context)
                .content(context.getString(R.string.loading_str))
                .widgetColorRes(R.color.black)
                .progress(true, 0)
                .build();
    }

}
