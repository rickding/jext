package com.onresolve.scriptrunner.canned.common.admin

import com.jext.constant.FieldSearcherEnum
import com.jext.constant.FieldTypeEnum
import com.jext.core.FieldHelper
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

        // Fields for permissions: users and groups
        FieldHelper.createCustomField("IssueEditors", "Users to edit issue", FieldTypeEnum.MultiUser, FieldSearcherEnum.User)
        FieldHelper.createCustomField("IssueBrowsers", "Users to browse issue", FieldTypeEnum.MultiUser, FieldSearcherEnum.User)
        FieldHelper.createCustomField("IssueCommenters", "Users to comment issue", FieldTypeEnum.MultiUser, FieldSearcherEnum.User)

        FieldHelper.createCustomField("GroupEditors", "Groups to edit issue", FieldTypeEnum.MultiGroup, FieldSearcherEnum.Group)
        FieldHelper.createCustomField("GroupBrowsers", "Groups to browse issue", FieldTypeEnum.MultiGroup, FieldSearcherEnum.Group)
        FieldHelper.createCustomField("GroupCommenters", "Groups to comment issue", FieldTypeEnum.MultiGroup, FieldSearcherEnum.Group)

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
