package com.jext.hello

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.fields.CustomField
import com.jext.util.LogUtil

assert issue
LogUtil.info("field", issue.getKey(), issue.getIssueType().getName(), issue.getStatus().getName())

// 某个项目下的某类单据
if (issue.projectObject.key != "DEMO" || issue.issueType.name != "Story" || issue.status.name == "Done" || !issue.dueDate) {
    return null
}

Date date = issue.dueDate
if (!date) {
    // 读取字段
    String fieldName = "StartDate"
    CustomField customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName(fieldName)
    date = (Date) issue.getCustomFieldValue(customField)
}

if (!date) {
    return null
}

return (int) ((new Date().getTime() - date.getTime()) / 1000 / 3600 / 24)
