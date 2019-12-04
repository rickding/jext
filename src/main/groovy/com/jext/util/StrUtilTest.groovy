package com.jext.util

class StrUtilTest {
    static void main(String[] args) {
        new StrUtilTest().testIsEmpty()
    }

    void testIsEmpty() {
        new HashMap<String, Boolean>() {{
            put(null, true)
            put("", true)
            put("str", false)
        }}.each {
            boolean ret = StrUtil.isEmpty(it.key)
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
