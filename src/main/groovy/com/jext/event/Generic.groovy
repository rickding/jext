package com.jext.event

import com.atlassian.crowd.embedded.api.Group
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.user.ApplicationUser
import com.jext.core.GroupHelper
import com.jext.core.IssueHelper
import com.jext.core.ValueHelper
import com.jext.util.LogUtil

assert event
LogUtil.info("event", event.eventTypeId, event)
Issue issue = event.issue

assert issue
LogUtil.info("event", issue.getKey(), issue.getIssueType().getName(), issue.getStatus().getName())

/**
 * Events: Custom listener
 * - generic event from workflow
 */
// 单据状态变化时，分配权限到不同的用户和用户组
List<ApplicationUser> users = new ArrayList<String>()
if (issue.reporter) {
    users.add(issue.reporter)
}
if (issue.assignee) {
    users.add(issue.assignee)
}

// 如果有父单据，读取父单据信息
if (issue.parent) {
    Issue parentIssue = IssueHelper.getIssue(issue.parent.key)
    if (parentIssue?.reporter) {
        users.add(parentIssue.reporter)
    }
    if (parentIssue?.assignee) {
        users.add(parentIssue.assignee)
    }
}

// 默认为单据创建人
if (!users) {
    users.add(issue.creator)
}

// 设置用户
Map<String, Object> fieldValueMap = new HashMap<String, Object>()
for (String fieldName : ["IssueEditors", "IssueBrowsers", "IssueCommenters"]) {
    LogUtil.info("分配用户权限", issue.key, fieldName, users)
    fieldValueMap.put(fieldName, users)
}

// 设置用户组
for (String fieldName : ["GroupEditors", "GroupBrowsers", "GroupCommenters"]) {
    List<Group> groups = GroupHelper.getGroups([fieldName])
    LogUtil.info("分配用户组权限", issue.key, fieldName, groups)
    fieldValueMap.put(fieldName, groups)
}

// 保存到字段
ValueHelper.saveCustomValue(issue, fieldValueMap)
