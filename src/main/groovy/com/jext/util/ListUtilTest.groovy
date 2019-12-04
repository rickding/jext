package com.jext.util

class ListUtilTest {
    static void main(String[] args) {
        ListUtilTest test = new ListUtilTest()
        test.testIsEmpty()
        test.testToArray()
    }

    void testIsEmpty() {
        LogUtil.info("Test isEmpty")
        new HashMap<List, Boolean>() {
            {
                put(null, true)
                put([], true)
                put(["item"], false)
            }
        }.each {
            boolean ret = ListUtil.isEmpty(it.key)
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testToArray() {
        LogUtil.info("Test toArray")
        new HashMap<Collection<Object>, Integer>() {
            {
                put(null, 0)
                put([""], 1)
                put(["str"], 1)
                put(["done", "关闭"], 2)
                put([3, 2], 2)
                put([3L, 4L], 2)
                put([1.0, 3.3, 4.4], 3)
                put([["str"], [3, 2]], 2)
            }
        }.each {
            String[] arr = (String[]) ListUtil.toArray(it.key)
            int ret = arr ? arr.length : 0
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
