package ua.sng.kiwitest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Oleksandr on 06.12.2017.
 */

public class KiwiHelper {

    public static String parseDate(String incomingDate){
        SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        Date date = null;
        try {
            date = incomingFormat.parse(incomingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat outgoingFormat = new SimpleDateFormat("dd MMMM yyyy", java.util.Locale.getDefault());

       return date != null ? outgoingFormat.format(date) : incomingDate;
    }

}
