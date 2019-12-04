package com.jext.core

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.issuetype.IssueType
import com.atlassian.jira.project.Project

class TypeHelper {
    static IssueType get(Project prjObj, String typeName) {
        if (!prjObj || !typeName) {
            return null
        }
        Collection<IssueType> items = ComponentAccessor.getIssueTypeSchemeManager().getIssueTypesForProject(prjObj)
        return items?.find({ typeName.equalsIgnoreCase(it.getName()) })
    }

    static IssueType[] get(Project prjObj) {
        if (!prjObj) {
            return null
        }
        Collection<IssueType> items = ComponentAccessor.getIssueTypeSchemeManager().getIssueTypesForProject(prjObj)
        if (!items || items.size() <= 0) {
            return null
        }

        IssueType[] arr = new IssueType[items.size()]
        items.toArray(arr)
        return arr
    }
}
