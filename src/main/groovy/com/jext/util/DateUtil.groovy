package com.jext.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

class DateUtil {
    /**
     * Diff the days
     */
    static int diffDates(String date1, String date2) {
        return diffDates(parse(date1, "yyyy-MM-dd"), parse(date2, "yyyy-MM-dd"));
    }
    static int diffDates(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return 0;
        }
        return (int) (diffSeconds(d1, d2) / (3600 * 24));
    }

    static long diffSeconds(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return 0;
        }
        return (long) ((d1.getTime() - d2.getTime()) / 1000);
    }

    static int dayOfMonth(String strDate) {
        return dayOfMonth(parse(strDate, "yyyy-MM-dd"));
    }
    static int dayOfMonth(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Add days
     */
    static Date adjustDate(String date, int days) {
        if (!date || date.trim().length() <= 0) {
            return null;
        }
        return adjustDate(parse(date, "yyyy-MM-dd"), days);
    }
    static Date adjustDate(Date date, int days) {
        if (date == null || days == 0) {
            return date;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    static Date adjustMonth(String date, int month) {
        if (!date || date.trim().length() <= 0) {
            return null;
        }
        return adjustMonth((Date)parse(date, "yyyy-MM-dd"), month);
    }
    static Date adjustMonth(Date date, int month) {
        if (date == null || month == 0) {
            return date;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    static Date adjustYear(String date, int years) {
        if (!date || date.trim().length() <= 0) {
            return null;
        }
        return adjustYear((Date)parse(date, "yyyy-MM-dd"), years);
    }
    static Date adjustYear(Date date, int years) {
        if (date == null || years == 0) {
            return date;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    static Date getMonthStart(String dateStr) {
        return !dateStr ? null : getMonthStart(parse(dateStr, "yyyy-MM-dd"))
    }
    static Date getMonthStart(Date date) {
        if (!date) { return null }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    static Date getMonthEnd(String dateStr) {
        return !dateStr ? null : getMonthEnd(parse(dateStr, "yyyy-MM-dd"))
    }
    static Date getMonthEnd(Date date) {
        if (!date) { return null }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, 31);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    static Date getNextMonthStart(String dateStr) {
        return !dateStr ? null : getNextMonthStart(parse(dateStr, "yyyy-MM-dd"))
    }
    static Date getNextMonthStart(Date date) {
        if (!date) { return null }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, 31);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * Is Saturday or Sunday
     */
    static boolean isWeekend(Date date) {
        if (date == null) { return false; }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return [Calendar.SATURDAY, Calendar.SUNDAY].contains(cal.get(Calendar.DAY_OF_WEEK));
    }

    /**
     * Return date without time
     */
    static Date getDate(Date date) {
        return parse(format(date))
    }

    /**
     * Format date as string
     */
    static String format(Date date) {
        return format(date, "yyyy-MM-dd")
    }
    static String format(Date date, String format) {
        if (date == null || format == null || format.trim().length() <= 0) {
            return "";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            System.out.printf("%s, %s\r\n", e.getMessage(), format);
        }
        return "";
    }

    /**
     * Parse string to date
     */
    static Date parse(String str) {
        return parse(str, "yyyy-MM-dd", false)
    }
    static Date parse(String str, String format) {
        return parse(str, format, false)
    }
    static Date parse(String str, String format, boolean showError) {
        if (str == null || str.trim().length() <= 0 || format == null || format.trim().length() <= 0) {
            return null;
        }

        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.parse(str.trim());
        } catch (ParseException e) {
            if (showError) {
                System.out.printf("%s, %s\r\n", e.getMessage(), format);
            }
        }
        return null;
    }
}
