package com.jext.hello

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.fields.CustomField
import com.jext.util.LogUtil

// Get issue and read custom field
//String issueKey = "DEMO-1"
//Issue issue = ComponentAccessor.getIssueManager().getIssueObject(issueKey)
//assert issue
//log.info String.format("%s, %s, %s", issue, issue.issueType.name, issue.status.name)

//String fieldName = "Story Points"
//CustomField customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName(fieldName)
//String fieldValue = (String)issue.getCustomFieldValue(customField)

/**
 * Validate data before submit
 */
Double fieldValue = (Double) cfValues["Story Points"]
LogUtil.info("value", fieldValue)
if (fieldValue == null) {
    return true
}

// Can be empty, or filled by special users
ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
if (!ComponentAccessor.getGroupManager().isUserInGroup(user, "story_point_group")) {
    return false
}

// Should be in range 1-100
Double value = Double.valueOf(fieldValue)
return value && value >= 1 && value <= 100
