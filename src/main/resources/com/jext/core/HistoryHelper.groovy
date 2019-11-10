package com.jext.core

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.changehistory.ChangeHistory
import com.atlassian.jira.issue.changehistory.ChangeHistoryManager
import com.atlassian.jira.issue.history.ChangeItemBean
import com.jext.util.ListUtil
import com.jext.util.LogUtil

class HistoryHelper {
    static ChangeItemBean getCloseItem(String issueKey) {
        return !issueKey ? null : getCloseItem(IssueHelper.getIssue(issueKey))
    }

    static ChangeItemBean getCloseItem(Issue issue) {
        return !issue ? null : getItem(issue, "status", "Done")
    }

    static List<ChangeItemBean> getTransitItems(String issueKey) {
        return !issueKey ? null : getTransitItems(IssueHelper.getIssue(issueKey))
    }

    static List<ChangeItemBean> getTransitItems(Issue issue) {
        return !issue ? null : getItems(issue, "status")
    }

    static List<ChangeHistory> getTransits(String issueKey) {
        return !issueKey ? null : getTransits(IssueHelper.getIssue(issueKey))
    }

    static List<ChangeHistory> getTransits(Issue issue) {
        return !issue ? null : getHistories(issue, "status")
    }

    static List<ChangeHistory> getHistories(String issueKey, String fieldName) {
        return !issueKey ? null : getHistories(IssueHelper.getIssue(issueKey), fieldName)
    }

    static List<ChangeHistory> getHistories(Issue issue, String fieldName) {
        if (!issue || !fieldName) {
            return null
        }

        ChangeHistoryManager changeHistoryManager = ComponentAccessor.getChangeHistoryManager()
        List<ChangeHistory> historyList = changeHistoryManager.getChangeItemsWithFieldsForIssues([issue], [fieldName])
        LogUtil.info("histories", issue.key, fieldName, historyList?.size())
        return !historyList ? null : historyList
    }

    static List<ChangeItemBean> getItems(List<ChangeHistory> histories) {
        if (!histories) {
            return null
        }

        List<ChangeItemBean> itemList = new ArrayList<ChangeItemBean>()
        histories.each {
            ChangeHistory item = (ChangeHistory) it
            List<ChangeItemBean> items = item.getChangeItemBeans()
            if (items) {
                itemList.addAll(items)
            }
        }
        return sortItems(itemList)
    }

    /**
     * Get the last value change
     */
    static ChangeItemBean getItem(String issueKey, String fieldName, String fieldValue) {
        return !issueKey ? null : getItem(IssueHelper.getIssue(issueKey), fieldName, fieldValue)
    }

    static ChangeItemBean getItem(Issue issue, String fieldName, String fieldValue) {
        if (!issue || !fieldName || fieldName.trim().isEmpty() || !fieldValue || fieldValue.trim().isEmpty()) {
            return null
        }

        // Get change history
        ChangeItemBean[] histories = getItems(issue, fieldName)
        if (!histories || histories.length <= 0) {
            return null
        }

        // Find the latest one
        for (int i = 0; i < histories.length; i++) {
            ChangeItemBean history = histories[i]
            String strValue = history.getToString()
            if (strValue && strValue.indexOf(fieldValue) >= 0) {
                LogUtil.info("history", issue.key, fieldName, fieldValue, history.getCreated())
                return history
            }
        }
        return null
    }

    /**
     * Get the field changes
     */
    static ChangeItemBean[] getItems(String issueKey, String fieldName) {
        return !issueKey ? null : getItems(IssueHelper.getIssue(issueKey), fieldName)
    }

    static ChangeItemBean[] getItems(Issue issue, String fieldName) {
        if (!issue || !fieldName) {
            return null
        }

        // Get change history
        ChangeHistoryManager changeHistoryManager = ComponentAccessor.getChangeHistoryManager()
        List<ChangeItemBean> historyList = changeHistoryManager.getChangeItemsForField(issue, fieldName)

        historyList = sortItems(historyList)
        LogUtil.info("histories", issue.key, fieldName, historyList?.size())
        return !historyList || historyList.size() <= 0 ? null : ListUtil.toArray(historyList)
    }

    static List<ChangeItemBean> sortItems(List<ChangeItemBean> items) {
        if (!items) {
            return null
        }

        // Sort descendant by created date
        items.sort(new Comparator<ChangeItemBean>() {
            @Override
            int compare(ChangeItemBean arg0, ChangeItemBean arg1) {
                return -arg0.getCreated().compareTo(arg1.getCreated())
            }
        })
        return items
    }
}
