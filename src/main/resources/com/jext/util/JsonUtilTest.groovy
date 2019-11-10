package com.jext.util

class JsonUtilTest {
    static void main(String[] args) {
        new JsonUtilTest().testJson()
    }

    void testJson() {
        new HashMap<Object, String>(){{
            put(null, null)
            put(["item"], "[\"item\"]")
            put(new HashMap<String, Object>() {{ put("key", "value") }}, "{\"key\":\"value\"}")
        }}.each {
            String ret = JsonUtil.toJson(it.key)
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret

            Object jsonObj = JsonUtil.parseJson(it.value)
            LogUtil.info jsonObj
            assert jsonObj == null || jsonObj instanceof Map || jsonObj instanceof List
        }
    }
}
