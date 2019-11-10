package com.jext.service

import com.jext.core.IndexHelper
import com.jext.util.LogUtil

assert issue
LogUtil.info("service", issue.getKey(), issue.getIssueType().getName(), issue.getStatus().getName())

/**
 * Escalation service: 4am every day, 0 0 4 * * ?
 * index issues, triggered by only one issue
 * JQL: key = DEMO-1
 */
IndexHelper.indexAll()
