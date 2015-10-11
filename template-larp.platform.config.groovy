/**
 * External config file
 */

// emulate JNDI resource
def jndiDescriptor = [
    type           : "javax.sql.DataSource",
    auth           : "Container",
    description    : "Data source for LARP Platform",
    driverClassName: "org.postgresql.Driver",
    url            : "jdbc:postgresql://localhost:5432/larp_platform_dev",
    username       : "postgres",
    password       : "postgres",
    maxActive      : "10",
    maxIdle        : "4"
]

/**
 * Environment related settings
 */
environments {

  // for database migrations
  dbmigr {
    // disable native GORM validation (fires before migration)
    dataSource {
      dbCreate = ""
    }

    // workaround for quartz plugin - it starts before migrations and crashes the app
    // if no database has been created yet
    grails.plugin.quartz2.autoStartup = false
    org.quartz = ['jobStore.class': 'org.quartz.simpl.RAMJobStore']

    // workaround for database migration plugin
    grails.naming.entries = [
        "java:comp/env/jdbc/larp_platform_db": jndiDescriptor
    ]
  }

  development {
    dataSource {
//      dbCreate = "create-drop"
    }
    grails.naming.entries = [
        "jdbc/larp_platform_db": jndiDescriptor
    ]
  }

  test {
    dataSource {
      // TODO configure after deploying autotests
      grails.mail.disabled = true
    }
  }

  production {
    dataSource {
      // TODO config it properly
    }
  }
}

/**
 * Mail settings
 */
grails.mail.default.from = "LARP Platform <sender@email.dom>"
grails.mail.disabled = false
// http://grails.org/plugins/mail
grails {
  mail {
    host = "smtp.domain.ru"
    port = 465
    username = "username@mailname.dom"
    password = "password"
    props = ["mail.smtp.auth"                  : "true",
             "mail.smtp.socketFactory.port"    : "465",
             "mail.smtp.socketFactory.class"   : "javax.net.ssl.SSLSocketFactory",
             "mail.smtp.socketFactory.fallback": "false"]
  }
}

/**
 * Custom settings
 */
// Пароль для админа (по умолчанию, при создании нового)
grails.larp.platform.adminInitialPassword = "a"

// Заполнять ли базу тестовыми данными
grails.larp.platform.setupTestData = false
// Очищать ли задачи Quartz-a, связанные с обновлением ресурсов
grailsApplication.config.grails.larp.platform.clearQuartzResourceTasks = false