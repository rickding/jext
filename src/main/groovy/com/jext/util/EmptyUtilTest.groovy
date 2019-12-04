package com.jext.util

class EmptyUtilTest {
    static void main(String[] args) {
        new EmptyUtilTest().testIsEmpty()
    }

    void testIsEmpty() {
        LogUtil.info("Test isEmpty")
        new HashMap<List, Boolean>() {
            {
                put(null, true)
                put(ListUtil.toArray(["item"]), false)
            }
        }.each {
            boolean ret = EmptyUtil.isEmpty(it.key)
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
