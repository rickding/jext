package com.onresolve.scriptrunner.canned.jira.workflow.listeners

import com.jext.util.LogUtil
import com.onresolve.scriptrunner.canned.CannedScript
import com.onresolve.scriptrunner.canned.util.BuiltinScriptErrors
import com.onresolve.scriptrunner.canned.util.SimpleBuiltinScriptErrors
import com.onresolve.scriptrunner.runner.customisers.ScriptListener
import groovy.util.logging.Log4j

@Log4j
@ScriptListener
class SampleListener implements CannedScript {
    public static String FIELD_MY_PARAM = "LISTENER_MY_PARAM"

    @Override
    String getName() {
        return "Sample listener"
    }

    @Override
    String getDescription() {
        return "Desc for sample listener"
    }

    @Override
    String getDescription(Map<String, String> params, boolean forPreview) {
        return String.format("desc for preview: %s, %s", forPreview, params)
    }

    @Override
    Map doScript(Map<String, Object> params) {
        LogUtil.info("doScript", params)
        return [:]
    }

    @Override
    List getParameters(Map params) {
        return [
            [
                name       : FIELD_MY_PARAM,
                label      : "Some parameter",
                description: "Description of this parameter"
            ]
        ]
    }

    @Override
    BuiltinScriptErrors doValidate(Map<String, String> params, boolean forPreview) {
        SimpleBuiltinScriptErrors errors = new SimpleBuiltinScriptErrors()
        return errors
    }

    @Override
    List getCategories() {
        return [] // unused
    }

    @Override
    Boolean isFinalParamsPage(Map params) {
        return true // unused
    }
}
