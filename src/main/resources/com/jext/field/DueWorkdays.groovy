package com.jext.field

import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.history.ChangeItemBean
import com.jext.core.HistoryHelper
import com.jext.core.IssueHelper
import com.jext.core.StatusHelper
import com.jext.core.WorkdayHelper
import com.jext.util.DateUtil
import com.jext.util.LogUtil

class DueWorkdays {
    static Integer getDueWorkdays(String issueKey) {
        return !issueKey ? null : getDueWorkdays(IssueHelper.getIssue(issueKey))
    }

    static Integer getDueWorkdays(Issue issue) {
        if (!issue) {
            return null
        }

        // Due date
        Date date = issue.getDueDate()
        if (!date) {
            return null
        }

        // get resolved date
        Date now = issue.getResolutionDate()
        if (!now && StatusHelper.isClosed(issue)) {
            // get closed date
            ChangeItemBean history = HistoryHelper.getCloseItem(issue)
            if (history) {
                now = new Date(history.getCreated().getTime())
                LogUtil.info("getDueWorkdays() uses closed date", issue.getKey(), DateUtil.format(now), history.getToString())
            }
        }

        // use "now"
        if (!now) {
            now = new Date()
        }

        // count the workdays
        date = DateUtil.getDate(date)
        now = DateUtil.getDate(now)
        WorkdayHelper helper = WorkdayHelper.getInst()
        int days = helper.countWorkdays(now, date)
        LogUtil.info(issue.getKey(), "due date", DateUtil.format(date), "now", DateUtil.format(now), "workdays", days)

        // Days to deadline
        if (days > 0) {
            return days - 1
        }

        // Days passed since deadline
        return 1 - helper.countWorkdays(date, now)
    }
}
