package com.jext.hello

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.jext.util.LogUtil
import com.onresolve.scriptrunner.runner.rest.common.CustomEndpointDelegate
import groovy.json.JsonBuilder
import groovy.transform.BaseScript

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.Response

@BaseScript CustomEndpointDelegate delegate

/**
 * http://localhost:8080/jira/rest/scriptrunner/latest/custom/issue/DEMO-1
 */
issue(httpMethod: "GET", groups: ["jira-software-users"]) { MultivaluedMap queryParams, String body, HttpServletRequest request ->
    String issueKey = getAdditionalPath(request)
    if (issueKey) {
        issueKey = issueKey.substring(1)
    }
    LogUtil.info("issue", issueKey)

    Issue issue = ComponentAccessor.getIssueManager().getIssueObject(issueKey)
    return Response.ok(new JsonBuilder([
        code   : !issue ? -1 : 0,
        msg    : !issue ? "error" : "ok",
        issue  : !issue ? "" : issue.getKey(),
        summary: !issue ? "" : issue.summary,
    ]).toString()).build()
}
