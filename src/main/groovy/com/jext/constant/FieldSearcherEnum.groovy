package com.jext.constant

enum FieldSearcherEnum {
    Number("com.atlassian.jira.plugin.system.customfieldtypes:exactnumber"),
    Group("com.atlassian.jira.plugin.system.customfieldtypes:grouppickersearcher"),
    User("com.atlassian.jira.plugin.system.customfieldtypes:userpickergroupsearcher"),

    ScriptNumber("com.onresolve.jira.groovy.groovyrunner:exactnumber"),

    private FieldSearcherEnum(String name) {
        this.name = name
    }

    String getName() {
        return name
    }

    private String name
}
