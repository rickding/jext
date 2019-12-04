package com.jext.util

class DateUtilTest {
    static void main(String[] args) {
        DateUtilTest test = new DateUtilTest()
        test.testAdjustYear()
        test.testAdjustMonth()
        test.testDiffDates()
        test.testGetMonthStart()
        test.testGetMonthEnd()
        test.testGetNextMonthStart()
    }

    void testAdjustYear() {
        LogUtil.info("Test adjustYear")
        new HashMap<String, String>() {
            {
                put(null, "")
                put("12-01", "")
                put("2019-02-26", "2022-02-26")
            }
        }.each {
            String ret = DateUtil.format(DateUtil.adjustYear((String) it.key, 3))
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testAdjustMonth() {
        LogUtil.info("Test adjustMonth")
        new HashMap<String, String>() {
            {
                put(null, "")
                put("12-01", "")
                put("2018-08-13", "2019-02-13")
                put("2019-02-26", "2019-08-26")
            }
        }.each {
            String ret = DateUtil.format(DateUtil.adjustMonth((String) it.key, 6))
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testDiffDates() {
        LogUtil.info("Test diffDates")
        new HashMap<String[], Integer>() {
            {
                put(ListUtil.toArray(["2019-02-23", "2019-02-20"]), 3)
            }
        }.each {
            String[] i = it.key
            int ret = DateUtil.diffDates(i[0], i[1])
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetMonthStart() {
        LogUtil.info("Test getMonthStart")
        new HashMap<String, String>() {
            {
                put("2019-02-24", "2019-02-01")
                put("2019-02-28", "2019-02-01")
                put("2019-03-01", "2019-03-01")
                put("2019-03-31", "2019-03-01")
                put("2019-04-01", "2019-04-01")
                put("2019-04-30", "2019-04-01")
            }
        }.each {
            String ret = DateUtil.format(DateUtil.getMonthStart(it.key))
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetMonthEnd() {
        LogUtil.info("Test getMonthEnd")
        new HashMap<String, String>() {
            {
                put("2019-02-24", "2019-02-28")
                put("2019-02-28", "2019-02-28")
                put("2019-03-01", "2019-03-31")
                put("2019-03-31", "2019-03-31")
                put("2019-04-01", "2019-04-30")
                put("2019-04-22", "2019-04-30")
            }
        }.each {
            String ret = DateUtil.format(DateUtil.getMonthEnd(it.key))
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetNextMonthStart() {
        LogUtil.info("Test getNextMonthStart")
        new HashMap<String, String>() {
            {
                put("2018-12-31", "2019-01-01")
                put("2018-12-01", "2019-01-01")
                put("2019-02-24", "2019-03-01")
                put("2019-02-28", "2019-03-01")
                put("2019-03-01", "2019-04-01")
                put("2019-03-31", "2019-04-01")
            }
        }.each {
            String ret = DateUtil.format(DateUtil.getNextMonthStart(it.key))
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
