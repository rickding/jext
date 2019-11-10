package com.jext.core

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.context.GlobalIssueContext
import com.atlassian.jira.issue.context.JiraContextNode
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.issuetype.IssueType
import com.jext.util.LogUtil

class FieldHelper {
    static Collection<CustomField> getCustomFields(String fieldName) {
        if (!fieldName) {
            return null
        }

        CustomFieldManager customFieldMgr = ComponentAccessor.getCustomFieldManager()
        Collection<CustomField> customFields = customFieldMgr.getCustomFieldObjectsByName(fieldName)
        LogUtil.info("get custom fields", fieldName, customFields?.size(), customFields)
        return customFields
    }

    static CustomField getCustomField(String fieldName) {
        if (!fieldName) {
            return null
        }

        Collection<CustomField> customFields = getCustomFields(fieldName)
        CustomField customField = !customFields ? null : customFields[0]
        LogUtil.info("get custom field", fieldName, customField)
        return customField
    }

    static CustomField createCustomField(String fieldName, String desc) {
        if (!fieldName) {
            return null
        }

        CustomField customField = getCustomField(fieldName)
        if (customField) {
            LogUtil.info("create custom field, existed:", fieldName, customField)
            return customField
        }

        /* No issue types to apply */
        def issueTypes = new ArrayList<IssueType>()
        issueTypes.add(null)

        /* Default to global context */
        def contextTypes = new ArrayList<JiraContextNode>()
        contextTypes.add(GlobalIssueContext.getInstance())

        CustomFieldManager customFieldMgr = ComponentAccessor.getCustomFieldManager()
        customFieldMgr.createCustomField(
            fieldName, !desc ? null : desc,
            customFieldMgr.getCustomFieldType("com.atlassian.jira.plugin.system.customfieldtypes:float"),
            customFieldMgr.getCustomFieldSearcher("com.atlassian.jira.plugin.system.customfieldtypes:exactnumber"),
            contextTypes,
            issueTypes
        )
    }

    static void removeCustomField(String fieldName) {
        if (!fieldName) {
            return
        }

        Collection<CustomField> customFields = getCustomFields(fieldName)
        LogUtil.info("remove custom field", fieldName, customFields?.size(), customFields)

        CustomFieldManager customFieldMgr = ComponentAccessor.getCustomFieldManager()
        customFields?.each {
            customFieldMgr.removeCustomField((CustomField) it)
        }
    }
}
