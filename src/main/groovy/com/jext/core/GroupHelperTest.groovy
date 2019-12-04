package com.jext.core

import com.atlassian.crowd.embedded.api.Group
import com.atlassian.jira.user.ApplicationUser
import com.jext.util.LogUtil

class GroupHelperTest {
    static void main(String[] args) {
        GroupHelperTest test = new GroupHelperTest()
        test.testCreateGroup()
        test.testGetGroup()
        test.testGetGroups()
        test.testGetUsers()
        test.testGetGroupNames()
        test.testAddToGroup()
        test.testIsInGroup()
    }

    void testCreateGroup() {
        LogUtil.info("Test createGroup()")
        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("IssueEditors", true)
            }
        }.each {
            Group group = GroupHelper.createGroup(it.key)
            Boolean ret = group != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetGroup() {
        LogUtil.info("Test getGroup")
        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("ab", false)
                put("IssueEditors", true)
            }
        }.each {
            Group group = GroupHelper.getGroup(it.key)
            Boolean ret = group != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetGroups() {
        LogUtil.info("Test getGroups")
        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("ab", false)
                put("IssueEditors", true)
            }
        }.each {
            List<Group> groups = GroupHelper.getGroups([it.key, "abc"])
            Boolean ret = groups != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetUsers() {
        LogUtil.info("Test getUsers")
        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("ab", false)
                put("IssueEditors", true)
            }
        }.each {
            List<ApplicationUser> users = GroupHelper.getUsers(it.key)
            Boolean ret = users != null && users.size() > 0
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testGetGroupNames() {
        LogUtil.info("Test getGroupNames() by user name")
        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("ab", false)
                put("admin", true)
            }
        }.each {
            List<Group> groups = GroupHelper.getGroups((String) it.key)
            Boolean ret = groups != null && groups.size() > 0
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret

            List<String> groupNames = GroupHelper.getGroupNames((String) it.key)
            ret = groupNames != null && groupNames.size() > 0
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }

    }

    void testAddToGroup() {
        LogUtil.info("Test addToGroup")
        String userName = "admin"
        ApplicationUser user = UserHelper.getUser(userName)

        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("IssueEditors", true)
            }
        }.each {
            Group group = GroupHelper.addToGroup(userName, it.key)
            Boolean ret = group != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret

            group = GroupHelper.addToGroup(user, it.key)
            ret = group != null
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }

    void testIsInGroup() {
        LogUtil.info("Test isInGroup")
        String userName = "admin"
        ApplicationUser user = UserHelper.getUser(userName)

        new HashMap<String, Boolean>() {
            {
                put(null, false)
                put("", false)
                put("ab", false)
                put("IssueEditors", true)
            }
        }.each {
            boolean ret = GroupHelper.isInGroup(userName, it.key)
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret

            ret = GroupHelper.isInGroup(user, it.key)
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
