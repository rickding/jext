# Jext extends Jira with ScriptRunner as development platform
* Customise
* Extend
* Administer
* Automate

# Prepare SDKs
1. Install IntelliJ IDEA 
2. Install maven
3. Install JDK8
    * https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    * java -version
4. Install Atlassian SDK
    * https://developer.atlassian.com/server/framework/atlassian-sdk/install-the-atlassian-sdk-on-a-windows-system/
    * atlas-version
5. Install ScriptRunner from Marketplace
    * http://marketplace.atlassian.com/apps/6820/scriptrunner-for-jira

# Setup Environment
1. Download source code
2. Update maven repo: settings.xml
3. mvn jira:debug
4. http://localhost:8080/jira, admin/admin
5. Debug with IDEA
    * Add configuration: remote, port: 5005
    * Set log, e.g. D:\work\scriptrunner-samples\jira\target\jira\home\log\atlassian-jira.log

# Run code
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
