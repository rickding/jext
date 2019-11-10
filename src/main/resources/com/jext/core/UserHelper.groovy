package com.jext.core

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.user.util.UserManager

class UserHelper {
    static ApplicationUser getAdmin() {
        ApplicationUser user = getUser("admin")
        return !user ? getUser("dingxianli") : user
    }

    static ApplicationUser getCurrentUser() {
        return ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
    }

    static ApplicationUser getUser(String userName) {
        if (!userName || userName.trim().isEmpty()) {
            return null
        }

        userName = userName.trim()
        UserManager userManager = ComponentAccessor.getUserManager()
        ApplicationUser user = userManager.getUserByKey(userName)
        return !user ? userManager.getUserByName(userName) : user
    }
}
