package com.jext.core

import com.atlassian.jira.issue.history.ChangeItemBean
import com.jext.util.LogUtil

import java.sql.Timestamp

class HistoryHelperTest {
    static void main(String[] args) {
        HistoryHelperTest test = new HistoryHelperTest()
        test.testGetCloseItem()
        test.testGetTransitItems()
        test.testGetItem()
        test.testGetItems()
    }

    void testGetCloseItem() {
        LogUtil.info("Test getClose()")
        new HashMap<String, Boolean>(){{
            put("", false)
            put("DEMO-1", false)
            put("DEMO-2", true)
        }}.each {
            ChangeItemBean data = HistoryHelper.getCloseItem(it.key)
            Boolean ret = data != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetTransitItems() {
        LogUtil.info("Test getTransitItems()")
        new HashMap<String, Boolean>(){{
            put("", false)
            put("DEMO-1", false)
            put("DEMO-2", true)
        }}.each {
            List<ChangeItemBean> data = HistoryHelper.getTransitItems(it.key)
            Boolean ret = data != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret

            if (data) {
                // parse values
                ChangeItemBean history = data.get(0)
                String historyValue = history?.getToString()
                Timestamp historyTime = history?.getCreated()
                Date historyDate = !historyTime ? null : new Date(historyTime.getTime())
                LogUtil.info("value", historyValue, "date", historyDate, "history", history)
            }
        }
    }

    void testGetItem() {
        LogUtil.info("Test getItem() with value")
        new HashMap<String, Integer>(){{
            put(null, 0)
            put("", 0)
            put("Done", 1)
        }}.each {
            ChangeItemBean history = HistoryHelper.getItem("DEMO-2", "status", it.key)
            int ret = !history ? 0 : 1
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetItems() {
        LogUtil.info("Test getItems()")
        new HashMap<String, Integer>(){{
            put(null, 0)
            put("", 0)
            put("DEMO-2", 1)
        }}.each {
            ChangeItemBean[] arr = HistoryHelper.getItems(it.key, "status")
            int ret = !arr || arr.length <= 0 ? 0 : 1
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
