package com.jext.constant

enum FieldTypeEnum {
    Number("com.atlassian.jira.plugin.system.customfieldtypes:float"),
    Group("com.atlassian.jira.plugin.system.customfieldtypes:grouppicker"),
    MultiGroup("com.atlassian.jira.plugin.system.customfieldtypes:multigrouppicker"),
    MultiUser("com.atlassian.jira.plugin.system.customfieldtypes:multiuserpicker"),
    User("com.atlassian.jira.plugin.system.customfieldtypes:userpicker"),

    Script("com.onresolve.jira.groovy.groovyrunner:scripted-field"),

    private FieldTypeEnum(String name) {
        this.name = name
    }

    String getName() {
        return name
    }

    private String name
}
