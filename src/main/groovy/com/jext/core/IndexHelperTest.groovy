package com.jext.core

import com.jext.util.LogUtil

class IndexHelperTest {
    static void main(String[] args) {
        IndexHelperTest test = new IndexHelperTest()
        test.testIndex()
    }

    void testIndex() {
        LogUtil.info("Test index()")
        IndexHelper.index("DEMO-1")
    }
}
