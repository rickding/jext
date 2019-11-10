package com.onresolve.scriptrunner.canned.common.admin

import com.jext.util.LogUtil
import com.onresolve.scriptrunner.canned.CannedScript
import com.onresolve.scriptrunner.canned.util.BuiltinScriptErrors

class SampleAdmin implements CannedScript {
    @Override
    String getName() {
        return "Sample admin"
    }

    @Override
    String getDescription() {
        return "Desc for sample admin"
    }

    @Override
    String getDescription(Map<String, String> params, boolean forPreview) {
        return String.format("desc for preview: %s, %s", forPreview, params)
    }

    @Override
    Map doScript(Map<String, Object> params) {
        LogUtil.info("doScript", params)
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
