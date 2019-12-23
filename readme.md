# Jext extends Jira with ScriptRunner as development platform
* Customise
* Extend
* Administer
* Automate

# Online course
https://edu.51cto.com/course/20936.html

# Features
## DueWorkdays
* Count the workdays between now and due date. 
* Use the resolved or closed date rather than now if the issue is resolved or closed.

## Permissions
* Project level:
    * Configure project users roles

* Issue level: Grant the users and groups with different permissions.
    * IssueEditors
    * IssueBrowsers
    * IssueCommenters
    * GroupEditors
    * GroupBrowsers
    * GroupCommenters
    * Extend: If the users and groups should be configured automatically, some scripts would be added when some events occur.

* Field level:
    * Configure screen with editable fields
    * Associate screen with issue type
    * Change issue type when issue is transited to different status
    * Extend: If some fields should be in issue detail page while should not be edited, the corresponding scripted fields would be added. 

# Run code
* Install ScriptRunner from Marketplace
    * http://marketplace.atlassian.com/apps/6820/scriptrunner-for-jira
* Script console
    * Type code directly, e.g. def issue = ComponentAccessor.getIssueManager().getIssueObject("DEMO-1")
    * Call script file, e.g. com/jext/hello/Hello.groovy
* Build in scripts, e.g. Sample admin
* Listeners, e.g. Sample listener
* Script fields, e.g. Sample field

# Code structure
1. Sample code, com.jext.hello
    * Hello.groovy
    * Field.groovy
    * Event.groovy
    * Service.groovy
    * Workflow.groovy
    * Api.groovy
    * UI.groovy
    
2. Build-in script, com.onresolve.scriptrunner.canned
    * common.admin (test and init)
    * jira.fields
    * jira.workflow.listeners (events)

3. Jext core
    * core (helper)
    * field
    * service
    * util

# Customise and extend
## Prepare SDKs
1. Install JDK8
    * https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    * java -version
2. Install Atlassian SDK
    * https://developer.atlassian.com/server/framework/atlassian-sdk/install-the-atlassian-sdk-on-a-windows-system/
    * atlas-version

## Setup Environment
1. Install IntelliJ IDEA 
2. Install maven
3. Download source code
4. Update maven repo: settings.xml
5. mvn jira:debug
6. http://localhost:8080/jira, admin/admin
7. Debug with IDEA
    * Add configuration: remote, port: 5005
    * Set log, e.g. D:\work\scriptrunner-samples\jira\target\jira\home\log\atlassian-jira.log
