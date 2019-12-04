package com.jext.core

import com.jext.util.LogUtil

class ValueHelperTest {
    static void main(String[] args) {
        ValueHelperTest test = new ValueHelperTest()
        test.testFillValueMap()
        test.testIsValueChanged()
    }

    void testFillValueMap() {
        LogUtil.info("Test fillValueMap()")
        ValueHelper.fillValueMap(null, ["fie"])
    }

    void testIsValueChanged() {
        LogUtil.info("Test isValueChanged")
        new HashMap<Map<String, Object>, Boolean>() {
            {
                put(null, true)
                put(ValueHelper.fillValueMap(null, ["fie"]), false)
                put(ValueHelper.fillValueMap(null, ["fie", "fly"]), false)
                put(ValueHelper.fillValueMap(new HashMap<String, Object>() {
                    {
                        put("fie", 22)
                    }
                }, ["fly"]), true)
            }
        }.each {
            boolean ret = ValueHelper.isValueChanged(it.key, ValueHelper.fillValueMap(null, ["fie"]))
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
