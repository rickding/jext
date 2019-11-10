package com.jext.core

import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.history.ChangeItemBean
import com.jext.util.LogUtil

class FieldHelperTest {
    static void main(String[] args) {
        FieldHelperTest test = new FieldHelperTest()
        test.testGetCustomField()
        test.testCreateCustomField()
        test.testRemoveCustomField()
    }

    void testGetCustomField() {
        LogUtil.info("Test getCustomField()")
        new HashMap<String, Boolean>(){{
            put(null, false)
            put("", false)
            put("New custom field", false)
            put("Story Points", true)
        }}.each {
            CustomField field = FieldHelper.getCustomField(it.key)
            Boolean ret = field != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testCreateCustomField() {
        LogUtil.info("Test getCustomField()")
        new HashMap<String, Boolean>(){{
            put(null, false)
            put("", false)
            put("New custom field number", true)
            put("Story Points", true)
        }}.each {
            CustomField field = FieldHelper.createCustomField(it.key, "field from test")
            Boolean ret = field != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testRemoveCustomField() {
        LogUtil.info("Test removeCustomField()")
        ["New custom field", "New custom field 2"].each {
            FieldHelper.removeCustomField((String) it)
        }
    }
}
