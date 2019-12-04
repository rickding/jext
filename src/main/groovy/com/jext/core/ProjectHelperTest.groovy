package com.jext.core

import com.atlassian.jira.project.Project
import com.jext.util.LogUtil

class ProjectHelperTest {
    static void main(String[] args) {
        ProjectHelperTest test = new ProjectHelperTest()
        test.testGet()
    }

    void testGet() {
        LogUtil.info("Test get")
        new HashMap<String, Boolean>() {{
            put(null, false)
            put("", false)
            put("demo", true)
            put("DEMO", true)
        }}.each {
            Project project = ProjectHelper.get(it.key)
            boolean ret = project != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
