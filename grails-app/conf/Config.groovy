grails.app.context = '/'

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// Если определена переменная среды с именем "${appName}.config.location", использовать ее
if (System.properties["${appName}.config.location"]) {
  println "Env var for config found. Loading config from: file:" + System.properties["${appName}.config.location"]
  grails.config.locations = ["file:" + System.properties["${appName}.config.location"]]
}
else {
  println "Loading config from: classpath:${appName}.config.groovy"
  grails.config.locations = ["classpath:${appName}.config.groovy"]
}

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// the default value is true
//grails.databinding.convertEmptyStringsToNull = false

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false
// enable hyphenated url-s
grails.web.url.converter = 'hyphenated'

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

// default constraits
//grails.gorm.default.constraints = {
//    '*'(nullable: true, size: 1..20)
//    myShared(blank: false)
//}


// Environmental
environments {
  development {
    grails.logging.jul.usebridge = true
  }

  production {
    grails.logging.jul.usebridge = false
    grails.serverURL = "http://city-rpg.srms.club"
  }
}

// TODO is it working?
// user config
grails.security.publicControllers = ['assets']

// log4j configuration
log4j.main = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'ru.srms.larp.platform.sec.SpringUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'ru.srms.larp.platform.sec.SpringUserSpringRole'
grails.plugin.springsecurity.authority.className = 'ru.srms.larp.platform.sec.SpringRole'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                              ['permitAll'],
	'/index':                         ['permitAll'],
	'/index.gsp':                     ['permitAll'],
	'/assets/**':                     ['permitAll'],
	'/register/**':                   ['permitAll'],
  '/plugins/**':                    ['permitAll'],
	'/**/js/**':                      ['permitAll'],
	'/**/css/**':                     ['permitAll'],
	'/**/images/**':                  ['permitAll'],
	'/**/favicon.ico':                ['permitAll'],

  // Spring Security UI
  '/acl-class/**': ['ROLE_ADMIN'],
  '/acl-sid/**': ['ROLE_ADMIN'],
  '/acl-object-identity/**': ['ROLE_ADMIN'],
  '/acl-entry/**': ['ROLE_ADMIN'],
  //'/persistentLogin/**': ['ROLE_ADMIN'],
  //'/requestmap/**': ['ROLE_ADMIN'],
  '/security-info/**': ['ROLE_ADMIN'],
  '/registration-code/**': ['ROLE_ADMIN'],
  '/role/**': ['ROLE_ADMIN'],
  '/user/**': ['ROLE_ADMIN'],
]

grails.plugin.springsecurity.acl.authority.changeAclDetails = 'ROLE_ACL_CHANGE_DETAILS'

// Asset pipeline settings
grails.assets.excludes = ["semantic/**"]

// Quartz config
grails.plugin.quartz2.autoStartup = true
// -- dataSource
org.quartz.dataSource.larpDS.jndiURL = 'java:comp/env/jdbc/larp_platform_db'
// -- options
org {
  quartz {
    //anything here will get merged into the quartz.properties so you don't need another file
    scheduler.instanceName = 'LarpPlatformScheduler'
    scheduler.idleWaitTime = 1000

    threadPool.class = 'org.quartz.simpl.SimpleThreadPool'
    threadPool.threadCount = 10
    threadPool.threadsInheritContextClassLoaderOfInitializingThread = true

    jobStore.class = 'org.quartz.impl.jdbcjobstore.JobStoreTX'
    jobStore.driverDelegateClass = 'org.quartz.impl.jdbcjobstore.PostgreSQLDelegate'
    jobStore.tablePrefix = 'QRTZ_'
    jobStore.dataSource = 'larpDS'
  }
}

// migrations
grails.plugin.databasemigration.changelogFileName = 'root.xml'
grails.plugin.databasemigration.databaseChangeLogTableName = 'database_changelog'
grails.plugin.databasemigration.databaseChangeLogLockTableName = 'database_changelog_lock'
grails.plugin.databasemigration.updateOnStart = false
//grails.plugin.databasemigration.dropOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = []
// -- ignore quartz tables
grails.plugin.databasemigration.ignoredObjects = ['qrtz_triggers','qrtz_simprop_triggers','qrtz_simple_triggers',
                                                  'qrtz_scheduler_state','qrtz_paused_trigger_grps','qrtz_locks',
                                                  'qrtz_job_details','qrtz_fired_triggers','qrtz_cron_triggers',
                                                  'qrtz_calendars','qrtz_blob_triggers','idx_qrtz_t_state',
                                                  'idx_qrtz_t_nft_st_misfire_grp','idx_qrtz_t_nft_st_misfire','idx_qrtz_t_nft_st',
                                                  'idx_qrtz_t_nft_misfire','idx_qrtz_t_next_fire_time','idx_qrtz_t_n_state',
                                                  'idx_qrtz_t_n_g_state','idx_qrtz_t_jg','idx_qrtz_t_g',
                                                  'idx_qrtz_t_c','idx_qrtz_j_req_recovery','idx_qrtz_j_grp',
                                                  'idx_qrtz_ft_trig_inst_name','idx_qrtz_ft_tg','idx_qrtz_ft_t_g',
                                                  'idx_qrtz_ft_jg','idx_qrtz_ft_j_g','idx_qrtz_ft_inst_job_req_rcvry',
                                                  'qrtz_triggers_sched_name_fkey','qrtz_simprop_triggers_sched_name_fkey','qrtz_simple_triggers_sched_name_fkey',
                                                  'qrtz_cron_triggers_sched_name_fkey','qrtz_blob_triggers_sched_name_fkey'
]

// WYSIWYG editor
htmlcleaner {
  whitelists = {

    whitelist("simple-rich-text") {
        startwith "simpleText"
        allow "s", "sub", "sup", "p"
    }

    whitelist("rich-text") {
      startwith "relaxed"

      allow "s", "figcaption", "hr"

      allow("figure") {
        attributes "class"
      }

      allow("code") {
        attributes "class"
      }
      allow("span") {
        attributes "class"
      }
      allow("p") {
        attributes "class"
      }
      allow("div") {
        attributes "class"
      }

      allow("table") {
        attributes "border", "cellpadding", "cellspacing"
      }
      allow("th") {
        attributes "scope"
      }
    }
  }
}