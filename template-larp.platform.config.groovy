/**
 * External config file
 */

/**
 * Data Sources
 */
environments {
  development {
    dataSource {
      url = "jdbc:postgresql://localhost:5432/mydb"
      username = "postgres"
      password = "postgres"
    }
  }

  test {
    dataSource {
      url = "jdbc:postgresql://localhost:5432/mydb"
      username = "postgres"
      password = "postgres"
    }
  }

  production {
    dataSource {
      url = "jdbc:postgresql://localhost:5432/mydb"
      username = "postgres1"
      password = "postgres"
    }
  }
}


/**
 * Mail settings
 */
grails.mail.default.from="no-reply@larp.srms.club"
grails.mail.disabled=true