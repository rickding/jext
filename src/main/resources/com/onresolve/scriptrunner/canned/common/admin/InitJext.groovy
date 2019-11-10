package com.onresolve.scriptrunner.canned.common.admin

import com.jext.util.LogUtil
import com.onresolve.scriptrunner.canned.CannedScript
import com.onresolve.scriptrunner.canned.util.BuiltinScriptErrors

class InitJext implements CannedScript {
    @Override
    String getName() {
        return "Jext Init"
    }

    @Override
    String getDescription() {
        return "Run initializations in Jext"
    }

    @Override
    String getDescription(Map<String, String> params, boolean forPreview) {
        return String.format("desc for preview: %s, %s", forPreview, params)
    }

    @Override
    Map doScript(Map<String, Object> params) {
        LogUtil.info("doScript: run initializations in Jext")
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
