package com.jext.core

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.project.Project

class ProjectHelper {
    static Project get(String prjKey) {
        if (!prjKey) {
            return null
        }

        Project prj = getByKey(prjKey)
        return !prj ? getByName(prjKey) : prj
    }

    static Project getByKey(String prjKey) {
        return !prjKey ? null : ComponentAccessor.getProjectManager().getProjectObjByKeyIgnoreCase(prjKey)
    }

    static Project getByName(String prjName) {
        return !prjName ? null : ComponentAccessor.getProjectManager().getProjectObjByName(prjName)
    }
}
