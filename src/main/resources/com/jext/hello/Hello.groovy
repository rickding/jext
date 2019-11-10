package com.jext.hello

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.IssueManager
import com.jext.util.JsonUtil
import com.jext.util.ListUtil
import com.jext.util.LogUtil
import com.jext.util.StrUtil

LogUtil.info String.format("list: %s, %s", ListUtil.isEmpty(null), ListUtil.isEmpty(["list"]))
LogUtil.info String.format("str: %s, %s", StrUtil.isEmpty(null), StrUtil.isEmpty("str"))

// Get issue
String issueKey = "DEMO-1"
IssueManager issueMgr = ComponentAccessor.getIssueManager()
Issue issue = issueMgr.getIssueObject(issueKey)
assert issue

Object[] msg = ["Hello world from Jext!", issueKey, issue?.key, issue?.getSummary()]
LogUtil.info(msg)
return JsonUtil.toJson(msg)
