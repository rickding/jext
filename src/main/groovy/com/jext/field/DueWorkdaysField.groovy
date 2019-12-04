package com.jext.hello

import com.jext.field.DueWorkdays
import com.jext.util.LogUtil

assert issue
LogUtil.info("field", issue.getKey(), issue.getIssueType().getName(), issue.getStatus().getName())

/**
 * DueWorkdays, Number Field
 */
return DueWorkdays.getDueWorkdays(issue)
