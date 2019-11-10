package com.onresolve.scriptrunner.canned.common.admin

import com.jext.core.*
import com.jext.field.DueWorkdaysTest
import com.jext.util.*
import com.onresolve.scriptrunner.canned.CannedScript
import com.onresolve.scriptrunner.canned.util.BuiltinScriptErrors

class TestJext implements CannedScript {
    @Override
    String getName() {
        return "Jext Test"
    }

    @Override
    String getDescription() {
        return "Run tests in Jext"
    }

    @Override
    String getDescription(Map<String, String> params, boolean forPreview) {
        return String.format("desc for preview: %s, %s", forPreview, params)
    }

    @Override
    Map doScript(Map<String, Object> params) {
        LogUtil.info("doScript: run tests in Jext")

        // core
        HistoryHelperTest.main(null)
        IndexHelperTest.main(null)
        IssueHelperTest.main(null)
        StatusHelperTest.main(null)
        UserHelperTest.main(null)
        WorkdayHelperTest.main(null)

        // field
        DueWorkdaysTest.main(null)

        // util
        DateUtilTest.main(null)
        EmptyUtilTest.main(null)
        JsonUtilTest.main(null)
        ListUtilTest.main(null)
        StrUtilTest.main(null)

        return ["value": "success"]
    }

    @Override
    List getParameters(Map params) {
        return []
    }

    @Override
    BuiltinScriptErrors doValidate(Map<String, String> params, boolean forPreview) {
        return null
    }

    @Override
    List getCategories() {
        return null // unused
    }

    @Override
    Boolean isFinalParamsPage(Map params) {
        return null // unused
    }
}
