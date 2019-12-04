package com.onresolve.scriptrunner.canned.common.admin

import com.jext.constant.FieldSearcherEnum
import com.jext.constant.FieldTypeEnum
import com.jext.core.FieldHelper
import com.jext.upgrade.FieldUpgradeTask
import com.jext.util.LogUtil
import com.onresolve.scriptrunner.canned.CannedScript
import com.onresolve.scriptrunner.canned.util.BuiltinScriptErrors

class InitJext implements CannedScript {
    @Override
    String getName() {
        return "Init Jext"
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

        // Fields from upgrade task
        new FieldUpgradeTask().doUpgrade()

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
