package com.jext.core

import com.jext.util.DateUtil

class WorkdayHelper {
    private static WorkdayHelper inst = null

    static WorkdayHelper getInst() {
        if (inst == null) {
            synchronized (WorkdayHelper.class) {
                if (inst == null) {
                    inst = new WorkdayHelper()
                }
            }
        }
        return inst
    }

    /**
     * Count work days, [startDate, endDate]
     */
    int countWorkdays(String startDate, String endDate) {
        Date start = DateUtil.parse(startDate, "yyyy-MM-dd")
        Date end = DateUtil.parse(endDate, "yyyy-MM-dd")
        if (!start || !end) {
            return 0
        }
        return countWorkdays(start, end)
    }

    int countWorkdays(Date startDate, Date endDate) {
        if (!startDate || !endDate) {
            return 0
        }
        startDate = DateUtil.getDate(startDate)
        endDate = DateUtil.getDate(endDate)

        int days = DateUtil.diffDates(endDate, startDate)
        if (days < 0) {
            days = 0
        } else {
            days += 1
            for (int i = days - 1; i >= 0; i--) {
                if (isHoliday(DateUtil.adjustDate(startDate, i))) {
                    days -= 1
                }
            }
        }
        return days
    }

    /**
     * Get the nearest workday, itself or next workday
     */
    Date getWorkday(Date date) {
        return getNextWorkday(date, 0)
    }

    Date getWorkday(String date) {
        return getNextWorkday(date, "yyyy-MM-dd", 0)
    }

    Date getWorkday(String date, String format) {
        return getNextWorkday(date, format, 0)
    }

    /**
     * Get next [nextDays] workday
     */
    Date getNextWorkday(Date date) {
        return getNextWorkday(date, 1)
    }

    Date getNextWorkday(String date) {
        return getNextWorkday(date, "yyyy-MM-dd", 1)
    }

    Date getNextWorkday(String date, String format) {
        return getNextWorkday(date, format, 1)
    }

    Date getNextWorkday(String date, int nextDays) {
        return getNextWorkday(date, "yyyy-MM-dd", nextDays)
    }

    Date getNextWorkday(String date, String format, int nextDays) {
        Date tmp = DateUtil.parse(date, format)
        return tmp == null ? null : getNextWorkday(tmp, nextDays)
    }

    Date getNextWorkday(Date date, int nextDays) {
        if (date == null) {
            return null
        }

        int count = 0
        Date workday = date
        boolean holiday = isHoliday(workday)
        while (holiday || count < nextDays) {
            // Count the workdays
            if (!holiday) {
                count++
            }

            // Next one, day by day
            workday = DateUtil.adjustDate(workday, 1)
            holiday = isHoliday(workday)
        }
        return workday
    }

    /**
     * Check if the date is workday. Note: null will be workday!
     */
    boolean isWorkday(String date) {
        return !isHoliday(date, "yyyy-MM-dd")
    }

    boolean isWorkday(String date, String format) {
        return !isHoliday(date, format)
    }

    boolean isWorkday(Date date) {
        return !isHoliday(date)
    }

    /**
     * Check if the date is holiday. Note: null will be workday!
     */
    boolean isHoliday(String date) {
        return isHoliday(date, "yyyy-MM-dd")
    }

    boolean isHoliday(String date, String format) {
        Date tmp = DateUtil.parse(date, format)
        return tmp == null ? false : isHoliday(tmp)
    }

    boolean isHoliday(Date date) {
        if (date == null) {
            return false
        }

        // Check if it's special work days
        if (isHoliday(date, specialWorkdays)) {
            return false
        }

        // Check if it's varied holiday days, or fixed ones, or weekends
        return isHoliday(date, variedHolidays) || isHoliday(date, fixedHolidays, "MM-dd") || DateUtil.isWeekend(date)
    }

    boolean isHoliday(Date date, Set<String> holidays) {
        return isHoliday(date, holidays, "yyyy-MM-dd")
    }

    boolean isHoliday(Date date, Set<String> holidays, String strFormat) {
        if (date == null || holidays == null || holidays.size() <= 0 || strFormat == null || strFormat.length() <= 0) {
            return false
        }
        return holidays.contains(DateUtil.format(date, strFormat))
    }

    // format: MM-dd
    private Set<String> fixedHolidays = new HashSet<String>() {
        {
            addAll(["01-01", "05-01", "10-01", "10-02", "10-03"])
        }
    }

    // format: yyyy-MM-dd
    private Set<String> variedHolidays = new HashSet<String>() {
        {
            addAll([
                "2018-02-15", "2018-02-16", "2018-02-19", "2018-02-20", "2018-02-21", // Spring Festival
                "2018-04-05", "2018-04-06", // Tomb's Day
                "2018-04-30", // Labor's Day
                "2018-06-18", // Dragon's Boat Day
                "2018-09-24", // Mid-Autumn Day
                "2018-10-04", "2018-10-05", // National Holiday
                "2018-12-31", // New Year
                "2019-02-04", "2019-02-05", "2019-02-06", "2019-02-07", "2019-02-08", // Spring Festival
                "2019-04-05", // Tomb's Day
                "2019-06-07", // Dragon's Boat Day
                "2019-09-13", // Mid-Autumn Day
                "2019-10-04", "2019-10-07", // National Holiday
            ])
        }
    }

    // format: yyyy-MM-dd
    private Set<String> specialWorkdays = new HashSet<String>() {
        {
            addAll([
                "2018-02-11", "2018-02-24", // Spring Festival
                "2018-04-08", // Tomb's day
                "2018-04-28", // Labor's day
                "2018-09-29", "2018-09-30", // National Holiday
                "2018-12-29", // New Year
                "2019-02-02", "2019-02-03", // Spring Festival
                "2019-09-29", "2019-10-12", // National Holiday
            ])
        }
    }

    private WorkdayHelper() {}

    @Override
    String toString() {
        """WorkdayHelper(
            fixedHolidays: ${fixedHolidays},
            variedHolidays: ${variedHolidays},
            specialWorkdays: ${specialWorkdays}
        )"""
    }
}
