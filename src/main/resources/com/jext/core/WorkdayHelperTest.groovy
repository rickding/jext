package com.jext.core

import com.jext.util.DateUtil
import com.jext.util.LogUtil

class WorkdayHelperTest {
    WorkdayHelper helper = WorkdayHelper.getInst()

    static void main(String[] args) {
        WorkdayHelperTest test = new WorkdayHelperTest()
        test.testCountWorkdays()
        test.testGetNextWorkday()
        test.testGetWorkday()
        test.testIsHoliday()
    }

    void testCountWorkdays() {
        LogUtil.info("Test countWorkdays")
        new HashMap<List<String>, Integer>() {
            {
                put(["", ""], 0)
                put(["", "2018-03-13"], 0)
                put(["2018-03-13", ""], 0)
                put(["2018-03-16", "2018-03-17"], 1) // Weekend
                put(["2018-03-16", "2018-03-18"], 1) // Weekend
                put(["2018-03-16", "2018-03-19"], 2) // Weekend
                put(["2018-03-17", "2018-03-18"], 0) // Weekend
                put(["2018-03-18", "2018-03-19"], 1) // Weekend
                put(["2018-04-04", "2018-04-05"], 1) // Special holiday
                put(["2019-01-22", "2019-01-22"], 1)
                put(["2019-01-22", "2019-01-23"], 2)
                put(["2019-01-24", "2019-01-23"], 0)
                put(["2019-01-24", "2019-01-22"], 0)
            }
        }.each {
            int ret = helper.countWorkdays(it.key.get(0), it.key.get(1))
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetNextWorkday() {
        LogUtil.info("Test getNextWorkday")
        new HashMap<String, String>() {
            {
                put("", "")
                put("sss", "")
                put("03-04", "") // Wrong string
                put("2018-01-01", "2018-01-04") // Fixed holiday
                put("2018-02-21", "2018-02-24") // Varied holiday
                put("2018-02-24", "2018-02-27") // Special workday
                put("2018-03-02", "2018-03-06") // Friday
                put("2018-03-03", "2018-03-07") // Saturday
                put("2018-03-04", "2018-03-07") // Sunday
                put("2018-03-05", "2018-03-07") // Monday
            }
        }.each {
            String ret = DateUtil.format(helper.getNextWorkday(it.getKey(), 2), "yyyy-MM-dd")
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetWorkday() {
        LogUtil.info("Test getWorkday")
        new HashMap<String, String>() {
            {
                put("", "")
                put("sss", "")
                put("03-04", "") // Wrong string
                put("2018-01-01", "2018-01-02") // Fixed holiday
                put("2018-02-21", "2018-02-22") // Varied holiday
                put("2018-02-24", "2018-02-24") // Special workday
                put("2018-03-04", "2018-03-05") // Sunday
                put("2018-03-05", "2018-03-05") // Monday
                put("2019-02-05", "2019-02-11") // Spring festival
            }
        }.each {
            String ret = DateUtil.format(helper.getWorkday(it.getKey()), "yyyy-MM-dd")
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testIsHoliday() {
        LogUtil.info("Test isHoliday")
        new HashMap<String, Boolean>() {
            {
                put("", false)
                put("sss", false)
                put("03-04", false) // Wrong string
                put("2018-01-01", true) // Fixed holiday
                put("2018-02-21", true) // Varied holiday
                put("2018-02-24", false) // Special workday
                put("2018-03-04", true) // Sunday
                put("2018-03-05", false) // Monday
                put("2019-02-05", true) // Spring festival
            }
        }.each {
            boolean ret = helper.isHoliday(it.getKey())
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
