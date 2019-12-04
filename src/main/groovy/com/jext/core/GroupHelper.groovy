package com.jext.core

import com.atlassian.crowd.embedded.api.Group
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.security.groups.GroupManager
import com.atlassian.jira.user.ApplicationUser
import com.jext.util.ListUtil

/**
 * https://docs.atlassian.com/software/jira/docs/api/7.2.0/com/atlassian/jira/component/ComponentAccessor.html
 * https://docs.atlassian.com/software/jira/docs/api/7.2.0/com/atlassian/jira/security/groups/GroupManager.html
 */
class GroupHelper {
    static List<ApplicationUser> getUsers(String groupName) {
        if (!groupName) {
            return null
        }
        Collection<ApplicationUser> users = ComponentAccessor.getGroupManager().getUsersInGroup(groupName)
        return !users ? null : new ArrayList<ApplicationUser>() {
            {
                addAll(users)
            }
        }
    }

    static List<String> getGroupNames(String userName) {
        if (!userName) {
            return null
        }

        Collection<Group> groups = ComponentAccessor.getGroupManager().getGroupNamesForUser(userName)
        return !groups ? null : new ArrayList<Group>() {
            {
                addAll(groups)
            }
        }
    }

    static List<String> getGroupNames(ApplicationUser user) {
        if (!user) {
            return null
        }

        Collection<Group> groups = ComponentAccessor.getGroupManager().getGroupNamesForUser(user)
        return !groups ? null : new ArrayList<Group>() {
            {
                addAll(groups)
            }
        }
    }

    static List<String> getGroupNames(List<Group> groups) {
        if (!groups) {
            return null
        }

        List<String> groupNames = new ArrayList<String>()
        groups.each {
            Group group = (Group) it
            groupNames.add((String) group.name)
        }
        return !groupNames ? null : groupNames
    }

    static String getGroupName(Group group) {
        return !group ? null : group.getName()
    }

    static List<Group> getGroups(String userName) {
        if (!userName) {
            return null
        }
        Collection<Group> groups = ComponentAccessor.getGroupManager().getGroupsForUser(userName)
        return !groups ? null : new ArrayList<Group>() {
            {
                addAll(groups)
            }
        }
    }

    static List<Group> getGroups(ApplicationUser user) {
        if (!user) {
            return null
        }
        Collection<Group> groups = ComponentAccessor.getGroupManager().getGroupsForUser(user)
        return !groups ? null : new ArrayList<Group>() {
            {
                addAll(groups)
            }
        }
    }

    static List<Group> getGroups(List<String> groupNames) {
        if (!groupNames) {
            return null
        }

        List<Group> groups = new ArrayList<Group>()
        groupNames.each {
            String groupName = (String) it
            Group group = getGroup(groupName)
            if (group) {
                groups.add(group)
            }
        }
        return !groups ? null : groups
    }

    static Group getGroup(String groupName) {
        return !groupName ? null : ComponentAccessor.getGroupManager().getGroup(groupName)
    }

    static Group createGroup(String groupName) {
        if (!groupName) {
            return null
        }

        Group group = getGroup(groupName)
        return group ? group : ComponentAccessor.getGroupManager().createGroup(groupName)
    }

    static Group addToGroup(String userName, String groupName) {
        if (!userName || !groupName) {
            return null
        }

        ApplicationUser user = ComponentAccessor.getUserManager().getUserByKey(userName)
        return addToGroup(!user ? ComponentAccessor.getUserManager().getUserByName(userName) : user, groupName)
    }

    static Group addToGroup(ApplicationUser user, String groupName) {
        if (!user || !groupName) {
            return null
        }

        Group group = getGroup(groupName)
        if (!group) {
            group = createGroup(groupName)
        }
        return addToGroup(user, group)
    }

    static Group addToGroup(String userName, Group group) {
        if (!userName || !group) {
            return null
        }

        ApplicationUser user = ComponentAccessor.getUserManager().getUserByKey(userName)
        return addToGroup(!user ? ComponentAccessor.getUserManager().getUserByName(userName) : user, group)
    }

    static Group addToGroup(ApplicationUser user, Group group) {
        if (!user || !group) {
            return null
        }

        GroupManager groupManager = ComponentAccessor.getGroupManager()
        if (!groupManager.groupExists(group)) {
            return null
        }

        if (!groupManager.isUserInGroup(user, group)) {
            groupManager.addUserToGroup(user, group)
        }
        return group
    }

    static boolean isInGroup(ApplicationUser user, String expectedGroup) {
        return !user || !expectedGroup ? false : isInGroup(user, (List<String>) [expectedGroup])
    }

    static boolean isInGroup(ApplicationUser user, List<String> expectedGroups) {
        return !user || !expectedGroups ? false : isInGroup(user, (String[]) ListUtil.toArray(expectedGroups))
    }

    static boolean isInGroup(ApplicationUser user, String[] expectedGroups) {
        if (!user || !expectedGroups) {
            return false
        }

        // Check user groups
        for (String userGroup in expectedGroups) {
            if (userGroup && ComponentAccessor.getGroupManager().isUserInGroup(user, userGroup.trim())) {
                return true
            }
        }
        return false
    }

    static boolean isInGroup(String user, String expectedGroup) {
        return !user || !expectedGroup ? false : isInGroup(user, (List<String>) [expectedGroup])
    }

    static boolean isInGroup(String user, List<String> expectedGroups) {
        return !user || !expectedGroups ? false : isInGroup(user, (String[]) ListUtil.toArray(expectedGroups))
    }

    static boolean isInGroup(String user, String[] expectedGroups) {
        if (!user || !expectedGroups) {
            return false
        }

        // Check user groups
        for (String userGroup in expectedGroups) {
            if (userGroup && ComponentAccessor.getGroupManager().isUserInGroup(user, userGroup.trim())) {
                return true
            }
        }
        return false
    }
}
