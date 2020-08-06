package org.alium.trackerlibrary;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * @author UzairWani
 *
 * This is a Data formattor to used for time zone in particular
 */

public class MiscUtilities {

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
