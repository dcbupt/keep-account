package com.bupt.dc.object.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class DateUtil {

    private static final List<String> months = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

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

    public static List<String> getMonths(String year) {
        return months.stream().map(s -> year + "-" + s).collect(Collectors.toList());
    }

    public static String now() {
        return DateFormatUtils.format(new Date(), YYYY_MM_DD_HH_MM_SS);
    }

    public static void main(String[] a) throws ParseException {
        System.out.println(getLastDayOfMonth("2019-02").toString());
    }

}
