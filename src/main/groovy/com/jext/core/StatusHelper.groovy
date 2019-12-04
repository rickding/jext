package com.jext.core

import com.atlassian.jira.issue.Issue
import com.jext.util.ListUtil

class StatusHelper {
    static boolean isClosed(String issueKey) {
        return !issueKey ? false : isClosed(IssueHelper.getIssue(issueKey))
    }

    static boolean isClosed(Issue issue) {
        return isExpectedStatus(issue, (String[]) ListUtil.toArray(["done", "关闭"]))
    }

    static boolean isResolved(String issueKey) {
        return !issueKey ? false : isResolved(IssueHelper.getIssue(issueKey))
    }

    static boolean isResolved(Issue issue) {
        return isExpectedStatus(issue, (String[]) ListUtil.toArray(["resolved", "完成"]))
    }

    static boolean isDeleted(String issueKey) {
        return !issueKey ? false : isDeleted(IssueHelper.getIssue(issueKey))
    }

    static boolean isDeleted(Issue issue) {
        return isExpectedStatus(issue, (String[]) ListUtil.toArray(["deleted", "废弃"]))
    }

    /**
     * Is the issue the expected status?
     */
    static boolean isExpectedStatus(String issueKey, List<String> statusArr) {
        return !issueKey || !statusArr ? false : isExpectedStatus(issueKey, (String[]) ListUtil.toArray(statusArr))
    }

    static boolean isExpectedStatus(Issue issue, List<String> statusArr) {
        return !issue || !statusArr ? false : isExpectedStatus(issue, (String[]) ListUtil.toArray(statusArr))
    }

    static boolean isExpectedStatus(String issueKey, String[] statusArr) {
        return !issueKey || !statusArr ? false : isExpectedStatus(IssueHelper.getIssue(issueKey), statusArr)
    }

    static boolean isExpectedStatus(Issue issue, String[] statusArr) {
        if (!issue || !statusArr || statusArr.length <= 0) {
            return false
        }

        String statusStr = issue.status?.name
        if (!statusStr) {
            return false
        }

        return statusArr.contains(statusStr) || statusArr.contains(statusStr.trim()) \
             || statusArr.contains(statusStr.trim()?.toLowerCase())
    }
}
