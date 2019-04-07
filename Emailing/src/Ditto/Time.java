/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ditto;

import static Ditto.Convert.toInt;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Owner
 */
public class Time {

    public static int getHour() {
        DateFormat dateFormat = new SimpleDateFormat("HH");
        Date date = new Date();
        return toInt(dateFormat.format(date));
    }

    public static int getMin() {
        DateFormat dateFormat = new SimpleDateFormat("mm");
        Date date = new Date();
        return toInt(dateFormat.format(date));
    }

    public static int getSec() {
        DateFormat dateFormat = new SimpleDateFormat("ss");
        Date date = new Date();
        return toInt(dateFormat.format(date));
    }

    public static int getDay() {
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        return toInt(dateFormat.format(date));
    }

    public static int getMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return toInt(dateFormat.format(date));
    }

    public static int getYear() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return toInt(dateFormat.format(date));
    }

    public static String getDate() {
        return getYear() + ":" + getMonth() + ":" + getDay();
    }

    public static String getTime() {
        return getHour() + ":" + getMin() + ":" + getSec();
    }

    public static String getInstant() {
        return getDate() + ":" + getTime();
    }
}
