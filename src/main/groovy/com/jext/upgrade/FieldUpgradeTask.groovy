package com.jext.upgrade

import com.atlassian.jira.issue.context.GlobalIssueContext
import com.atlassian.sal.api.message.Message
import com.atlassian.sal.api.upgrade.PluginUpgradeTask
import com.jext.constant.FieldSearcherEnum
import com.jext.constant.FieldTypeEnum
import com.jext.core.FieldHelper
import com.jext.util.LogUtil
import com.onresolve.scriptrunner.runner.ScriptRunnerImpl
import com.onresolve.scriptrunner.test.ScriptFieldCreationInfo

class FieldUpgradeTask extends AbstractUpgradeTask implements PluginUpgradeTask {
    @Override
    int getBuildNumber() {
        return 1
    }

    @Override
    String getShortDescription() {
        "Create scripted field"
    }

    @Override
    Collection<Message> doUpgrade() throws Exception {
        // create managed script fields, behaviours, and anything else not covered by the YAML file
        LogUtil.warn("upgrading with build number", getBuildNumber())

        // Field for DueWorkdays
        new HashMap<String, String>() {{
            put("DueWorkdays", "com/jext/field/DueWorkdaysField.groovy")
        }}.each {
            String fieldName = it.key
            String scriptFile = it.value
            if (FieldHelper.getCustomField(fieldName)) {
                LogUtil.info("existed field", fieldName, scriptFile)
            } else {
                def scriptFieldCreation = ScriptFieldCreationInfo.Builder.newBuilder()
                    .setName(fieldName)
                    .setDescription("The workdays from now to due date")
                    .setNote("DueWorkdaysField by Jext")
                    .setTemplate("float")
                    .setSearcherKey(ScriptRunnerImpl.PLUGIN_KEY + ":exactnumber")
                    .setContexts([GlobalIssueContext.instance])
                    .setScriptFile(scriptFile)
                    .build()

                scriptFieldCreation.create()
            }
        }

        // Fields for permissions: users and groups
        FieldHelper.createCustomField("IssueEditors", "Users to edit issue", FieldTypeEnum.MultiUser, FieldSearcherEnum.User)
//        FieldHelper.createCustomField("IssueBrowsers", "Users to browse issue", FieldTypeEnum.MultiUser, FieldSearcherEnum.User)
//        FieldHelper.createCustomField("IssueCommenters", "Users to comment issue", FieldTypeEnum.MultiUser, FieldSearcherEnum.User)
//
//        FieldHelper.createCustomField("GroupEditors", "Groups to edit issue", FieldTypeEnum.MultiGroup, FieldSearcherEnum.Group)
//        FieldHelper.createCustomField("GroupBrowsers", "Groups to browse issue", FieldTypeEnum.MultiGroup, FieldSearcherEnum.Group)
//        FieldHelper.createCustomField("GroupCommenters", "Groups to comment issue", FieldTypeEnum.MultiGroup, FieldSearcherEnum.Group)
        return null
    }

    /*
     This method isn't necessary, but is handy for testing the upgrade task via the Script Console,
     so you don't have to restart your local Jira instance to test it out.
    */
    static void main(String[] args) {
        new FieldUpgradeTask().doUpgrade()
    }
}
