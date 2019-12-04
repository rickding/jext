package com.jext.service

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.index.IssueIndexManager
import com.jext.util.LogUtil

assert issue
LogUtil.info("service", issue.getKey(), issue.getIssueType().getName(), issue.getStatus().getName())

/**
 * 和日期有关的字段每天早上主动触发更新计算: 0 0 4 * * ?
 * JQL: dueDate is not EMPTY
 */
IssueIndexManager issueIndexManager = ComponentAccessor.getComponent(IssueIndexManager.class)
issueIndexManager.reIndex(issue)
