grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.repos.default = "quonb-snapshot"

grails.project.dependency.distribution = {
    String serverRoot = "http://mvn.quonb.org"
    remoteRepository(id: 'quonb-snapshot', url: serverRoot + '/plugins-snapshot-local/')
    remoteRepository(id: 'quonb-release', url: serverRoot + '/plugins-release-local/')
}

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()

        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://mvn.quonb.org/repo"
        grailsRepo "http://mvn.quonb.org/repo", "quonb"

        mavenCentral()
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        compile 'org.pegdown:pegdown:1.2.1'
        compile "org.jsoup:jsoup:1.7.2"
        compile "org.parboiled:parboiled-java:1.1.3"
        compile "org.parboiled:parboiled-core:1.1.3"
        compile "asm:asm-all:3.3.1"

        test("org.spockframework:spock-grails-support:0.7-groovy-2.0") {
            export = false
        }

        // runtime 'mysql:mysql-connector-java:5.1.18'
    }

    plugins {
        compile ":infra-file-storage:0.2-SNAPSHOT"
        compile ":infra-images:0.2-SNAPSHOT"

        build(":tomcat:$grailsVersion",
                ":release:latest.release", ":hibernate:$grailsVersion") {
            export = false
        }

        test(":spock:latest.release") {
            exclude "spock-grails-support"
            export = false
        }
    }
}
