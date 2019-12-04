package com.jext.core

import com.atlassian.jira.issue.issuetype.IssueType
import com.atlassian.jira.project.Project
import com.jext.util.LogUtil

class TypeHelperTest {
    static void main(String[] args) {
        TypeHelperTest test = new TypeHelperTest()
        test.testGet()
        test.testGetByName()
    }

    void testGet() {
        LogUtil.info("Test get array")
        new HashMap<String, Boolean>() {{
            put(null, false)
            put("", false)
            put("demo", true)
            put("demo23", false)
        }}.each {
            Project prj = ProjectHelper.get(it.key)
            IssueType[] types = TypeHelper.get(prj)
            boolean ret = types && types.length > 0
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetByName() {
        LogUtil.info("Test get by name")
        Project prj = ProjectHelper.get("demo")
        new HashMap<String, String>() {{
            put(null, null)
            put("", null)
            put("Story", "Story")
            put("Story22", null)
        }}.each {
            IssueType issueType = TypeHelper.get(prj, it.key)
            boolean isRight = it.value == null || it.value.equals(issueType?.getName())
            LogUtil.info(
                !isRight ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", issueType
            )
            assert isRight
        }
    }
}
