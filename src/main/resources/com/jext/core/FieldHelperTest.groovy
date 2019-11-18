package com.jext.core

import com.atlassian.jira.issue.fields.CustomField
import com.jext.constant.FieldSearcherEnum
import com.jext.constant.FieldTypeEnum
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
        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("New custom field", false)
                put("Story Points", true)
            }
        }.each {
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
        LogUtil.info("Test createCustomField()")
        new HashMap<List<Object>, Boolean>() {
            {
                put([null, null, null, null], false)
                put(["", null, null, null], false)
                put(["Story Points", null, null, null], true)
                put(["New custom field number", "field from test", FieldTypeEnum.Number, FieldSearcherEnum.Number], true)
                put(["New custom field script", "field from test", FieldTypeEnum.Script, FieldSearcherEnum.ScriptNumber], true)
            }
        }.each {
            List<Object> params = it.key
            CustomField field = FieldHelper.createCustomField((String)params[0], (String)params[1], (FieldTypeEnum)params[2], (FieldSearcherEnum)params[3])
            Boolean ret = field != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", params[0], "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testRemoveCustomField() {
        LogUtil.info("Test removeCustomField()")
        [
            "Custom fields", "New custom field",
            "DueWorkdaysField", "SampleField", "ScriptField",
//            "Transition",
//            "IssueEditors", "IssueBrowsers", "IssueCommenters",
//            "GroupEditors", "GroupBrowsers", "GroupCommenters"
        ].each {
            FieldHelper.removeCustomField((String) it)
        }
    }
}
