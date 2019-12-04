package com.jext.core

import com.atlassian.crowd.embedded.api.Group
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.customfields.impl.CascadingSelectCFType
import com.atlassian.jira.issue.customfields.option.Option
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.user.ApplicationUser
import com.jext.util.JsonUtil
import org.apache.log4j.Logger

import java.sql.Timestamp

/**
 * https://docs.atlassian.com/software/jira/docs/api/7.2.0/com/atlassian/jira/component/ComponentAccessor.html
 * https://docs.atlassian.com/software/jira/docs/api/7.2.0/com/atlassian/jira/issue/MutableIssue.html
 * https://docs.atlassian.com/software/jira/docs/api/7.2.0/com/atlassian/jira/issue/fields/CustomField.html
 * https://docs.atlassian.com/software/jira/docs/api/7.2.0/com/atlassian/jira/issue/customfields/option/Option.html
 *
 * https://docs.atlassian.com/software/jira/docs/api/8.0.1/com/atlassian/jira/issue/customfields/impl/CascadingSelectCFType.html
 * https://community.atlassian.com/t5/Answers-Developer-Questions/Getting-java-lang-ClassCastException-java-util-HashMap-cannot-be/qaq-p/571726 *
 */
class ValueHelper {
    static boolean isValueChanged(Map<String, Object> valueMap, Map<String, Object> curValueMap) {
        if (!valueMap && !curValueMap) {
            return false
        }
        if (!valueMap && curValueMap || valueMap && !curValueMap) {
            return true
        }

        boolean isChanged = false
        for (String infoName : valueMap.keySet()) {
            if (!(!curValueMap.get(infoName) && !valueMap.get(infoName)
                || String.valueOf(curValueMap.get(infoName)) == String.valueOf(valueMap.get(infoName))
            )) {
                isChanged = true
                break
            }
        }
        return isChanged
    }

    static Map<String, Object> fillValueMap(Map<String, Object> valueMap, List<String> fieldList) {
        if (!fieldList) {
            return valueMap
        }

        if (valueMap == null) {
            valueMap = new HashMap<String, Object>()
        }

        fieldList.each {
            String fieldName = (String) it
            if (!valueMap.containsKey(fieldName)) {
                valueMap.put(fieldName, null)
            }
        }
        return valueMap
    }

    /**
     * Read the data as json from issue's custom field
     */
    static Object getJsonValue(String issueKey, String fieldName) {
        return !issueKey || !fieldName ? null : getJsonValue((Issue) ComponentAccessor.getIssueManager().getIssueObject(issueKey), fieldName)
    }

    static Object getJsonValue(Issue issue, String fieldName) {
        if (issue == null || !fieldName || fieldName.trim().length() <= 0) {
            return null
        }

        String json = getCustomValue(issue, fieldName)
        return !json || json.trim().length() <= 0 ? null : JsonUtil.parseJson(json)
    }

    /**
     * Save the data as json to issue's custom field
     */
    static void saveJsonValue(Issue issue, String fieldName, Object fieldData) {
        if (!issue || !fieldName) {
            return
        }
        saveJsonValue(issue.getKey(), fieldName, fieldData)
    }

    static void saveJsonValue(String issueKey, String fieldName, Object fieldData) {
        if (!issueKey || !fieldName || fieldName.trim().length() <= 0) {
            return
        }
        saveCustomValue(issueKey, fieldName, !fieldData ? null : JsonUtil.toJson(fieldData))
    }

    static List<Group> getGroups(String issueKey, String fieldName) {
        return !issueKey || !fieldName ? null : getGroups(getIssue(issueKey), fieldName)
    }

    static List<Group> getGroups(Issue issue, String fieldName) {
        Object groups = getCustomValue(issue, fieldName)
        return !groups ? null : new ArrayList<Group>() {
            {
                addAll(groups)
            }
        }
    }

    static List<String> getGroupNames(String issueKey, String fieldName) {
        return !issueKey || !fieldName ? null : getGroupNames(getIssue(issueKey), fieldName)
    }

    static List<String> getGroupNames(Issue issue, String fieldName) {
        List<Group> groupList = getGroups(issue, fieldName)
        if (!groupList) {
            return null
        }

        List<String> nameList = new ArrayList<String>()
        groupList.each {
            Group group = (Group) it
            nameList.add((String) group.getName())
        }
        return !nameList ? null : nameList
    }

    static Group getGroup(String issueKey, String fieldName) {
        return !issueKey || !fieldName ? null : getGroup(getIssue(issueKey), fieldName)
    }

    static Group getGroup(Issue issue, String fieldName) {
        Object group = getCustomValue(issue, fieldName)
        if (!group) {
            return null
        }

        if (group instanceof Collection && group.size() > 0) {
            return new ArrayList<Group>() {
                {
                    addAll(group)
                }
            }.get(0)
        }
        return (Group) group
    }

    static String getGroupName(String issueKey, String fieldName) {
        return !issueKey || !fieldName ? null : getGroupName(getIssue(issueKey), fieldName)
    }

    static String getGroupName(Issue issue, String fieldName) {
        Group group = getGroup(issue, fieldName)
        return !group ? null : group.getName()
    }

    static List<ApplicationUser> getUsers(String issueKey, String fieldName) {
        return !issueKey || !fieldName ? null : getUsers(getIssue(issueKey), fieldName)
    }

    static List<ApplicationUser> getUsers(Issue issue, String fieldName) {
        Object users = getCustomValue(issue, fieldName)
        return !users ? null : new ArrayList<ApplicationUser>() {
            {
                addAll(users)
            }
        }
    }

    static List<String> getUserNames(String issueKey, String fieldName) {
        return !issueKey || !fieldName ? null : getUserNames(getIssue(issueKey), fieldName)
    }

    static List<String> getUserNames(Issue issue, String fieldName) {
        List<ApplicationUser> userList = getUsers(issue, fieldName)
        if (!userList) {
            return null
        }

        List<String> nameList = new ArrayList<String>()
        userList.each {
            ApplicationUser user = (ApplicationUser) it
            nameList.add((String) user.getName())
        }
        return !nameList ? null : nameList
    }

    /**
     * Get field value and set to map
     */
    static void getFieldValue(Map<String, Object> data, String issueKey, String fieldName) {
        getFieldValue(data, issueKey, fieldName, false)
    }

    static void getFieldValue(Map<String, Object> data, String issueKey, String fieldName, boolean isOption) {
        if (!issueKey || !fieldName) {
            return
        }
        getFieldValue(data, (Issue) ComponentAccessor.getIssueManager().getIssueObject(issueKey), fieldName, isOption)
    }

    static void getFieldValue(Map<String, Object> data, Issue issue, String fieldName) {
        getFieldValue(data, fieldName, issue, fieldName, false)
    }

    static void getFieldValue(Map<String, Object> data, Issue issue, String fieldName, boolean isOption) {
        getFieldValue(data, fieldName, issue, fieldName, isOption)
    }

    static void getFieldValue(Map<String, Object> data, String fieldKey, String issueKey, String fieldName) {
        getFieldValue(data, fieldKey, issueKey, fieldName, false)
    }

    static void getFieldValue(Map<String, Object> data, String fieldKey, String issueKey, String fieldName, boolean isOption) {
        if (!issueKey || !fieldName) {
            return
        }
        getFieldValue(data, fieldKey, (Issue) ComponentAccessor.getIssueManager().getIssueObject(issueKey), fieldName, isOption)
    }

    static void getFieldValue(Map<String, Object> data, String fieldKey, Issue issue, String fieldName) {
        getFieldValue(data, fieldKey, issue, fieldName, false)
    }

    static void getFieldValue(Map<String, Object> data, String fieldKey, Issue issue, String fieldName, boolean isOption) {
        if (data == null || !fieldKey || !issue || !fieldName) {
            return
        }

        Object value = getCustomValue(issue, fieldName, isOption)
        if (value != null) {
            data.put(fieldKey, value)
        }
    }

    /**
     * Get the custom field value
     */
    static Object getCustomValue(String issueKey, String fieldName, boolean withOption) {
        return !issueKey || !fieldName ? null : getCustomValue((Issue) ComponentAccessor.getIssueManager().getIssueObject(issueKey), fieldName, withOption)
    }

    static Object getCustomValue(Issue issue, String fieldName, boolean withOption) {
        if (!issue || !fieldName || fieldName.trim().length() <= 0) {
            return null
        }

        Object ret = getCustomValue(issue, fieldName)
        if (!withOption || !ret) {
            return ret
        }
        if (ret instanceof Option) {
            return ((Option) ret).getValue()
        } else if (ret instanceof List) {
            List<String> valueList = new ArrayList<String>()
            (List) ret.each { valueList.add(((Option) it).getValue()) }
            return valueList
        } else if (ret instanceof Map) {
            Map<String, Object> optionMap = (Map<String, Object>) ret
            List<String> valueList = new ArrayList<String>()
            if (optionMap.containsKey(CascadingSelectCFType.PARENT_KEY)) {
                valueList.add(optionMap.get(CascadingSelectCFType.PARENT_KEY)?.getValue())
            }
            if (optionMap.containsKey(CascadingSelectCFType.CHILD_KEY)) {
                valueList.add(optionMap.get(CascadingSelectCFType.CHILD_KEY)?.getValue())
            }
            return valueList
        }
        return null
    }

    static Object getCustomValue(String issueKey, String fieldName) {
        return !issueKey || !fieldName ? null : getCustomValue((Issue) ComponentAccessor.getIssueManager().getIssueObject(issueKey), fieldName)
    }

    static Object getCustomValue(Issue issue, String fieldName) {
        if (!issue || !fieldName || fieldName.trim().length() <= 0) {
            return null
        }

        CustomField customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName(fieldName)
        return customField ? issue.getCustomFieldValue(customField) : null
    }

    static void saveCustomValue(Issue issue, String fieldName, Object parentOption, Object childOption) {
        if (!issue || !fieldName) {
            return
        }
        saveCustomValue(issue.getKey(), fieldName, parentOption, childOption)
    }

    static void saveCustomValue(String issueKey, String fieldName, Object parentOption, Object childOption) {
        if (!issueKey || !fieldName) {
            return
        }

        HashMap<String, Object> cascadingOption = !parentOption && !childOption ? null : new HashMap<String, Object>() {
            {
                put(CascadingSelectCFType.PARENT_KEY, parentOption)
                put(CascadingSelectCFType.CHILD_KEY, childOption)
//            put(CascadingSelectCFType.PARENT_KEY, parentOption.getValue()) // parent key is null
//            put(CascadingSelectCFType.CHILD_KEY, childOption.getValue()) // child key is "1"
            }
        }
        saveCustomValue(issueKey, fieldName, cascadingOption, true)
    }

    static void saveCustomValue(Issue issue, Map<String, Object> customFieldMap, boolean withOption) {
        if (!issue || !customFieldMap || customFieldMap.size() <= 0) {
            return
        }
        saveCustomValue(issue.getKey(), customFieldMap, withOption)
    }

    static void saveCustomValue(String issueKey, Map<String, Object> customFieldMap, boolean withOption) {
        if (!issueKey || !customFieldMap || customFieldMap.size() <= 0) {
            return
        }

        if (!withOption) {
            saveCustomValue(issueKey, customFieldMap)
            return
        }

        MutableIssue mutableIssue = ComponentAccessor.getIssueManager().getIssueObject(issueKey)
        if (mutableIssue) {
            CustomFieldManager customFieldMgr = ComponentAccessor.getCustomFieldManager()
            customFieldMap.each {
                String fieldName = (String) it.key
                Object fieldValue = it.value

                // Support multiple and cascading options. Can be Option or string value.
                Object optionValue = null
                if (fieldValue instanceof Collection) {
                    Collection collection = (Collection) fieldValue
                    List<Option> optList = new ArrayList<Option>()
                    for (int i = collection.size() - 1; i >= 0; i--) {
                        Object obj = collection.get(i)
                        Option opt = obj instanceof Option ? obj : getCustomOption((Issue) mutableIssue, fieldName, (String) obj)
                        if (opt != null) {
                            optList.add(opt)
                        }
                    }
                    optionValue = optList
                } else if (fieldValue instanceof Map) {
                    Map<String, Object> optionMap = (Map<String, Object>) fieldValue
                    Object parentObj = optionMap.get(CascadingSelectCFType.PARENT_KEY)
                    if (parentObj != null) {
                        // Parent option
                        Option parentOption = parentObj instanceof Option ? parentObj : getCustomOption((Issue) mutableIssue, fieldName, (String) parentObj)
                        optionMap.put(CascadingSelectCFType.PARENT_KEY, parentOption)

                        // Child option
                        if (parentOption == null) {
                            optionMap.put(CascadingSelectCFType.CHILD_KEY, null)
                        } else {
                            Object childObj = optionMap.get(CascadingSelectCFType.CHILD_KEY)
                            if (childObj != null) {
                                Option childOption = childObj instanceof Option ? childObj : parentOption.getChildOptions()?.find({
                                    it.getValue() != null && it.getValue().equals((String) childObj)
                                })
                                optionMap.put(CascadingSelectCFType.CHILD_KEY, childOption)
                            }
                        }
                    }
                    optionValue = optionMap
                } else if (fieldValue instanceof Option) {
                    optionValue = fieldValue
                } else {
                    optionValue = getCustomOption((Issue) mutableIssue, fieldName, (String) fieldValue)
                }

                CustomField customField = customFieldMgr.getCustomFieldObjectByName(fieldName)
                if (customField) {
                    mutableIssue.setCustomFieldValue(customField, optionValue)
                }
            }
            updateIssue((Issue) mutableIssue)
        }
    }

    static void saveCustomValue(Issue issue, String fieldName, Object fieldValue, boolean withOption) {
        if (!issue || !fieldName || fieldName.trim().length() <= 0) {
            return
        }
        saveCustomValue((String) issue.getKey(), fieldName, fieldValue, withOption)
    }

    static void saveCustomValue(String issueKey, String fieldName, Object fieldValue, boolean withOption) {
        if (!issueKey || !fieldName || fieldName.trim().length() <= 0) {
            return
        }

        Map<String, Object> customFieldMap = new HashMap<String, Object>() {
            {
                put(fieldName, fieldValue)
            }
        }
        if (!withOption) {
            saveCustomValue(issueKey, customFieldMap)
        } else {
            saveCustomValue(issueKey, customFieldMap, true)
        }
    }

    static void saveCustomValue(Issue issue, Map<String, Object> customFieldMap) {
        if (!issue || !customFieldMap || customFieldMap.size() <= 0) {
            return
        }
        saveCustomValue((String) issue.getKey(), customFieldMap)
    }

    static void saveCustomValue(String issueKey, Map<String, Object> customFieldMap) {
        if (!issueKey || !customFieldMap || customFieldMap.size() <= 0) {
            return
        }

        MutableIssue mutableIssue = ComponentAccessor.getIssueManager().getIssueObject(issueKey)
        if (mutableIssue) {
            CustomFieldManager customFieldMgr = ComponentAccessor.getCustomFieldManager()
            customFieldMap.each {
                String fieldName = (String) it.key
                Object fieldValue = it.value
                if (fieldValue instanceof Date) {
                    fieldValue = new Timestamp(((Date) fieldValue)?.getTime())
                }

                CustomField customField = customFieldMgr.getCustomFieldObjectByName(fieldName)
                if (customField) {
                    mutableIssue.setCustomFieldValue(customField, fieldValue)
                }
            }
            updateIssue((Issue) mutableIssue)
        }
    }

    static void saveCustomValue(Issue issue, String fieldName, Object fieldValue) {
        if (!issue || !fieldName || fieldName.trim().length() <= 0) {
            return
        }
        saveCustomValue((String) issue.getKey(), fieldName, fieldValue)
    }

    static void saveCustomValue(String issueKey, String fieldName, Object fieldValue) {
        if (!issueKey || !fieldName || fieldName.trim().length() <= 0) {
            return
        }

        CustomField customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObjectByName(fieldName)
        if (!customField) {
            return
        }

        MutableIssue mutableIssue = ComponentAccessor.getIssueManager().getIssueObject(issueKey)
        if (mutableIssue) {
            if (fieldValue instanceof Date) {
                fieldValue = new Timestamp(((Date) fieldValue)?.getTime())
            }
            mutableIssue.setCustomFieldValue(customField, fieldValue)
            updateIssue((Issue) mutableIssue)
        }
    }

    /**
     * Clean the custom field values
     */
    static void cleanCustomFieldValues(Issue issue, List<String> fieldNameList, Logger log) {
        if (!issue || !fieldNameList || fieldNameList.size() <= 0) {
            return
        }
        cleanCustomFieldValues(issue, fieldNameList, true, log)
    }

    static void cleanCustomFieldValues(Issue issue, List<String> fieldNameList, boolean comment, Logger log) {
        if (!issue || !fieldNameList || fieldNameList.size() <= 0) {
            return
        }

        String[] fieldNameArr = new String[fieldNameList.size()]
        fieldNameList.toArray(fieldNameArr)
        cleanCustomFieldValues(issue, fieldNameArr, comment, log)
    }

    static void cleanCustomFieldValues(Issue issue, String[] fieldNameArr, Logger log) {
        cleanCustomFieldValues(issue, fieldNameArr, true, log)
    }

    static void cleanCustomFieldValues(Issue issue, String[] fieldNameArr, boolean comment, Logger log) {
        if (!issue || !fieldNameArr || fieldNameArr.size() <= 0) {
            return
        }

        // Clear the custom fields
        Map<String, Object> fieldValueMap = new HashMap<String, Object>()

        for (String fieldName : fieldNameArr) {
            Object fieldValue = getCustomValue(issue, fieldName)
            log?.info String.format("cleanCustomFieldValues: %s, %s: %s\n", issue.key, fieldName, fieldValue)

            // Empty value when it exists
            if (fieldValue) {
                fieldValueMap.put(fieldName, null)

                // Comment the issue
                if (comment) {
                    String tmp = fieldValue.toString();
                    if (tmp && tmp.length() > 30) {
                        tmp = String.format("%s...", tmp.substring(0, 10))
                    }

                    String msg = String.format("%s, %s: %s => null\n", issue.key, fieldName, tmp)
                    msg = String.format("[%s] %s", ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()?.name, msg)
                    try {
                        ComponentAccessor.commentManager.create(issue, ComponentAccessor.userManager.getUserByKey("admin"), msg, true)
                    } catch (Exception e) {
                        log?.error String.format("IssueValueHelper.cleanCustomFieldValues: %s", e.getMessage())
                    }
                }
            }
        }

        // set empty values
        saveCustomValue(issue, fieldValueMap)
    }
}
