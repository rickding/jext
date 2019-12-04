package com.jext.hello

import com.atlassian.jira.issue.Issue
import com.atlassian.plugin.web.Condition
import com.atlassian.jira.plugin.webfragment.model.JiraHelper
import com.jext.util.LogUtil

assert issue
LogUtil.info("field", issue.getKey(), issue.getIssueType().getName(), issue.getStatus().getName())

/**
 * Show button, Request Header "Referer" contains issue key
 * - Custom web item, operations-top-level, weight: 1
 * - Link: http://jext.top
 */
class Check implements Condition {
    void init(Map<String,String> params) {
    }

    boolean	shouldDisplay(Map<String,Object> context) {
        Issue issue = (Issue) context.get("issue")
        return issue && issue.projectObject.key == "DEMO" && issue.issueType.name == "Story"
    }
}

// directly return true/false
//return issue.projectObject.key == "DEMO" && issue.issueType.name == "Story"
