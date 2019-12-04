package com.jext.hello

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.issue.IssueEvent
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.fields.CustomField
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

// 事件发生时做一些处理，比如设置或清空某些字段
["Story Points", "custom field"].each {
    CustomField customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName((String) it)
    issue.setCustomFieldValue(customField, null)
}

// 脚本修改字段后主动触发更新缓存
ComponentAccessor.getIssueManager().updateIssue(
    ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser(),
    issue,
    EventDispatchOption.DO_NOT_DISPATCH,
    false
)
