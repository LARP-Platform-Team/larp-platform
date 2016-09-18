grails.servlet.version = '3.0' // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = 'target/classes'
grails.project.test.class.dir = 'target/test-classes'
grails.project.test.reports.dir = 'target/test-reports'
grails.project.work.dir = 'target/work'
grails.project.target.level = 1.8
grails.project.source.level = 1.8
//grails.project.war.file = 'target/${appName}-${appVersion}.war'

grails.project.fork = false
//    [
//    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
//    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
//
//    // configure settings for the test-app JVM, uses the daemon by default
//    test   : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon: true],
//    // configure settings for the run-app JVM
//    run    : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
//    // configure settings for the run-war JVM
//    war    : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
//    // configure settings for the Console UI JVM
//    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
//]

grails.project.dependency.resolver = 'maven' // or ivy
grails.project.dependency.resolution = {
  // inherit Grails' default dependencies
  inherits('global') {
    // specify dependency exclusions here; for example, uncomment this to disable ehcache:
    // excludes 'ehcache'
  }
  log 'error' // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
  checksums true // Whether to verify checksums on resolve
  legacyResolve false
  // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

  repositories {
    inherits true // Whether to inherit repository definitions from plugins

    grailsPlugins()
    grailsHome()
    mavenLocal()
    grailsCentral()
    mavenCentral()
    // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
    //mavenRepo 'http://repository.codehaus.org'
    mavenRepo 'http://download.java.net/maven/2/'
    //mavenRepo 'http://repository.jboss.com/maven2/'
    //mavenRepo 'http://repo.spring.io/milestone/'
  }

  dependencies {
    // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
    build 'commons-dbcp:commons-dbcp:1.4'
    runtime 'org.postgresql:postgresql:9.3-1101-jdbc41'
    compile 'com.google.guava:guava-collections:r03'
    // only for hibernate4
    test 'org.grails:grails-datastore-test-support:1.0.2-grails-2.4'
  }

  plugins {
    // plugins for the build system only
    build ':tomcat:7.0.55.3'

    // plugins needed at runtime but not for compilation
    // if this is an issue (https://jira.grails.org/browse/GRAILS-11600) use 3rd version
    // runtime ':hibernate:3.6.10.18'
    runtime(':hibernate4:4.3.10') {
      excludes "ehcache-core"
    }
    runtime ':database-migration:1.4.1'

    // plugins for the compile step
    compile ':cache:1.1.8'
    compile ":cache-ehcache:1.0.5-SNAPSHOT"

    compile ':spring-security-core:2.0-RC5'
    compile ':spring-security-acl:2.0-RC2'
    compile ":mail:1.0.7"

    compile ':asset-pipeline:1.9.9'
    compile ':quartz2:2.1.6.2'
    compile ":html-cleaner:0.3"

    // Uncomment these to enable additional asset-pipeline capabilities
    //compile ':sass-asset-pipeline:1.9.0'
    //compile ':less-asset-pipeline:1.10.0'
    //compile ':coffee-asset-pipeline:1.8.0'
    //compile ':handlebars-asset-pipeline:1.3.0.3'
  }
}
