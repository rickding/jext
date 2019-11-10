package com.onresolve.scriptrunner.canned.jira.fields

import com.atlassian.jira.issue.Issue
import com.jext.field.DueWorkdays
import com.jext.util.LogUtil
import com.onresolve.scriptrunner.canned.util.BuiltinScriptErrors
import com.onresolve.scriptrunner.canned.util.SimpleBuiltinScriptErrors
import com.onresolve.scriptrunner.runner.ScriptRunnerImpl
import com.onresolve.scriptrunner.runner.customisers.ScriptedField
import groovy.util.logging.Log4j

/**
 * 到期工作日天数，DueWorkdays, Number Field
 */
@Log4j
@ScriptedField
class DueWorkdaysField extends AbstractCannedScriptField {
    String getName() {
        return "DueWorkdays"
    }

    String getDescription() {
        return "Workdays between now and due date"
    }

    String getModelTemplate() {
        return "float";
    }

    String getCustomFieldSearcher() {
        return ScriptRunnerImpl.PLUGIN_KEY + ":exactnumber"
    }

    Map doScript(Map<String, Object> params) {
        Issue issue = (Issue) params?.get("issue")
        LogUtil.info("doScript", issue, params)

        Integer workdays = DueWorkdays.getDueWorkdays(issue)
        return ["value": workdays]
    }

    List doGetParameters(Map params) {
        return []
    }

    BuiltinScriptErrors doValidation(Map<String, Object> params, boolean forPreview) {
        SimpleBuiltinScriptErrors errors = new SimpleBuiltinScriptErrors()
        return errors
    }

    String getCustomFieldSearcher(Map params) {
        return null // unused
    }
}
