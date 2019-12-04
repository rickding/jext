package com.jext.core

import com.jext.util.LogUtil

class IssueHelperTest {
    static void main(String[] args) {
        IssueHelperTest test = new IssueHelperTest()
        test.testGetIssueById()
        test.testGetIssueByKey()
    }

    void testGetIssueById() {
        LogUtil.info("Test getIssue() by id")
        new HashMap<Integer, String>() {
            {
                put(null, null)
                put(0, null)
                put(1, null)
                put(10000, "DEMO-1")
            }
        }.each {
            String ret = IssueHelper.getIssue(it.getKey())?.getKey()
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetIssueByKey() {
        LogUtil.info("Test getIssue() by key")
        new HashMap<String, String>() {
            {
                put(null, null)
                put("", null)
                put("sss", null)
                put("DEMO-1", "DEMO-1")
            }
        }.each {
            String ret = IssueHelper.getIssue(it.getKey())?.getKey()
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testSetIssueType() {
        LogUtil.info("Test setIssueType()")
        new HashMap<String, Boolean>() {{
            put(null, false)
            put("", false)
            put("DEMO-4", true)
            put("DEMO-5", true)
        }}.each {
            boolean ret = IssueHelper.setIssueType(it.key, "Backup")
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
