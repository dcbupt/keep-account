package com.bupt.dc.object.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    public DateUtil() {
    }

    public static String getGmtTime(Date d) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    public static Date getCstDate(String s) throws ParseException {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.parse(s);
    }

    public static LocalDate formatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    public static LocalDate getFirstDayOfMonth(String month) {
        String dateFormat = month+"-01";
        LocalDate localDate = formatDate(dateFormat);
        LocalDate start = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return start;
    }

    public static LocalDate getLastDayOfMonth(String month) {
        String dateFormat = month+"-01";
        LocalDate localDate = formatDate(dateFormat);
        LocalDate end = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return end;
    }

    public static void main(String[] a) throws ParseException {
        System.out.println(getLastDayOfMonth("2019-02").toString());
    }

}
