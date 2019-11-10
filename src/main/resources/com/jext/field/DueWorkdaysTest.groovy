package com.jext.field

import com.atlassian.jira.issue.MutableIssue
import com.jext.core.IssueHelper
import com.jext.core.WorkdayHelper
import com.jext.util.DateUtil
import com.jext.util.LogUtil

import java.sql.Timestamp

class DueWorkdaysTest {
    static void main(String[] args) {
        DueWorkdaysTest test = new DueWorkdaysTest()
        test.testGetDueWorkdays()
    }

    void testGetDueWorkdays() {
        // set due dates for issues
        Date today = new Date()
        WorkdayHelper helper = WorkdayHelper.getInst()

        MutableIssue issue = IssueHelper.getIssue("DEMO-1")
        issue?.setDueDate(new Timestamp(helper.getNextWorkday(today, 1).getTime()))
        IssueHelper.updateIssue(issue)

        issue = IssueHelper.getIssue("DEMO-3")
        issue?.setDueDate(new Timestamp(DateUtil.adjustDate(today, -1).getTime()))
        IssueHelper.updateIssue(issue)

        LogUtil.info("Test getDueWorkdays")
        new HashMap<String, Integer>() {
            {
                put(null, null)
                put("12-01", null)
                put("DEMO-1", 1)
                put("DEMO-2", -3)
                put("DEMO-3", -1)
            }
        }.each {
            Integer ret = DueWorkdays.getDueWorkdays(it.getKey())
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
