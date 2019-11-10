package com.jext.core

import com.jext.util.LogUtil

class UserHelperTest {
    static void main(String[] args) {
        UserHelperTest test = new UserHelperTest()
        test.testGetCurrentUser()
        test.testGetAdmin()
        test.testGetUser()
    }

    void testGetCurrentUser() {
        LogUtil.info(UserHelper.getCurrentUser())
    }

    void testGetAdmin() {
        LogUtil.info(UserHelper.getAdmin())
    }

    void testGetUser() {
        LogUtil.info("Test getUser")
        new HashMap<String, String>() {
            {
                put(null, null)
                put("", null)
                put("ads", null)
                put("admin", "admin")
            }
        }.each {
            String ret = UserHelper.getUser(it.getKey())?.getName()
            LogUtil.info(
                it.getValue() != ret ? "Error" : "Right",
                "key", it.getKey(), "value", it.getValue(), "actual", ret
            )
            assert it.value == ret
        }
    }
}
