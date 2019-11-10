package com.jext.core

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.index.IssueIndexManager

class IndexHelper {
    static void index(List<Issue> issues) {
        if (!issues) {
            return
        }

        issues.each {
            index((Issue) it)
        }
    }

    static void index(String issueKey) {
        if (!issueKey) {
            return
        }

        index(IssueHelper.getIssue(issueKey))
    }

    static void index(Issue issue) {
        if (!issue) {
            return
        }

        IssueIndexManager issueIndexManager = ComponentAccessor.getComponent(IssueIndexManager.class)
        issueIndexManager.reIndex(issue)
    }

    static void indexAll() {
        IssueIndexManager issueIndexManager = ComponentAccessor.getComponent(IssueIndexManager.class)
        issueIndexManager.reIndexAll()
    }
}
