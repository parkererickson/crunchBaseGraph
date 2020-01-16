import com.optum.giraffle.tasks.GsqlTask

plugins {
    id("com.optum.giraffle") version "1.3.2"
    id("net.saliman.properties") version "1.5.1"
}

repositories {
    jcenter()
}

val gsqlGraphname: String by project // <1>
val gsqlHost: String by project
val gsqlUserName: String by project
val gsqlPassword: String by project
val gsqlAdminUserName: String by project
val gsqlAdminPassword: String by project
val gsqlCaCert: String by project
val tokenMap: LinkedHashMap<String, String> =
    linkedMapOf("graphname" to gsqlGraphname) // <2>

val grpSchema: String = "Tigergraph Schema"
val grpQueries: String = "Tigergraph Queries"
val grpLoad: String = "Tigergraph Loading"

tigergraph { // <3>
    scriptDir.set(file("db_scripts"))
    tokens.set(tokenMap)
    serverName.set(gsqlHost)
    userName.set(gsqlUserName)
    password.set(gsqlPassword)
    adminUserName.set(gsqlAdminUserName)
    adminPassword.set(gsqlAdminPassword)
    caCert.set(gsqlCaCert)
}

tasks {
    val createCompanyLinks by registering(GsqlTask::class){
        group = grpQueries
        description = "Creates the companyLinks query"
        scriptPath = "companyLinks.gsql"
        superUser = true
    }
    val installTrainAnswerTuples by registering(GsqlTask::class){
        group = grpQueries
        description = "Installs the companyLinks query"
        scriptPath = "installCompanyLinks.gsql"
        superUser = true
    }
}