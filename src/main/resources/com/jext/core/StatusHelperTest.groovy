package com.jext.core

import com.jext.util.LogUtil

class StatusHelperTest {
    static void main(String[] args) {
        StatusHelperTest test = new StatusHelperTest()
        test.testIsClosed()
        test.testIsResolved()
    }

    void testIsClosed() {
        LogUtil.info("Test isClosed")
        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("DEMO-1", false)
                put("DEMO-2", true)
            }
        }.each {
            boolean ret = StatusHelper.isClosed(it.key)
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testIsResolved() {
        LogUtil.info("Test isResolved")
        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("DEMO-1", false)
                put("DEMO-2", false)
            }
        }.each {
            boolean ret = StatusHelper.isResolved(it.key)
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
