package com.jext.core

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.MutableIssue

class IssueHelper {
    static Issue getIssue(Long issueId) {
        return !issueId ? null : ComponentAccessor.getIssueManager().getIssueObject(issueId)
    }

    static Issue getIssue(String issueKey) {
        return !issueKey ? null : ComponentAccessor.getIssueManager().getIssueObject(issueKey)
    }

    static boolean updateIssue(String issueKey) {
        return !issueKey ? false : updateIssue(getIssue(issueKey))
    }

    static boolean updateIssue(Issue issue) {
        if (!issue) {
            return false
        }

        MutableIssue mutableIssue = (issue instanceof MutableIssue) ? (MutableIssue) issue :
            ComponentAccessor.getIssueManager().getIssueObject(issue.getKey())

        return null != ComponentAccessor.getIssueManager().updateIssue(
            UserHelper.getAdmin(), mutableIssue,
            EventDispatchOption.DO_NOT_DISPATCH, false
        )
    }
}
