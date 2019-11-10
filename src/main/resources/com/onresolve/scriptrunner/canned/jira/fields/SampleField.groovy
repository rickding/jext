package com.onresolve.scriptrunner.canned.jira.fields

import com.atlassian.jira.issue.Issue
import com.jext.util.LogUtil
import com.onresolve.scriptrunner.canned.util.BuiltinScriptErrors
import com.onresolve.scriptrunner.canned.util.SimpleBuiltinScriptErrors
import com.onresolve.scriptrunner.runner.ScriptRunnerImpl
import com.onresolve.scriptrunner.runner.customisers.ScriptedField
import groovy.util.logging.Log4j

/**
 * SampleField, DateTime
 */
@Log4j
@ScriptedField
class SampleField extends AbstractCannedScriptField {
    public static String FIELD_MY_PARAM = "FIELD_MY_PARAM"

    String getName() {
        return "Sample field"
    }

    String getDescription() {
        return "Desc for sample field"
    }

    String getModelTemplate() {
        return "datetime"
    }

    String getCustomFieldSearcher() {
        return ScriptRunnerImpl.PLUGIN_KEY + ":datetimerange"
    }

    Map doScript(Map<String, Object> params) {
        Issue issue = (Issue) params?.get("issue")
        LogUtil.info("doScript", issue, params)
        return ["value": new Date()]
    }

    List doGetParameters(Map params) {
        return [
            [
                name       : FIELD_MY_PARAM,
                label      : "Some parameter",
                description: "Description of this parameter"
            ]
        ]
    }

    BuiltinScriptErrors doValidation(Map<String, Object> params, boolean forPreview) {
        SimpleBuiltinScriptErrors errors = new SimpleBuiltinScriptErrors()
        return errors
    }

    String getCustomFieldSearcher(Map params) {
        return null // unused
    }
}
