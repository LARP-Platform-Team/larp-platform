package ru.srms.larp.platform.db.migrations

import liquibase.Liquibase
import liquibase.database.Database
import org.apache.log4j.Logger

/**
 *
 * <p>Created 01.09.15</p>
 * @author TrebleSnake
 */
public class MigrationCallbacksHandler {
  protected static final Logger logger = Logger.getLogger(MigrationCallbacksHandler.class)

  void beforeStartMigration(Database database) {
    logger.info("Migrations started on da: ${database.liquibaseSchemaName}")
  }

  void onStartMigration(Database database, Liquibase liquibase, String changelogName) {
    logger.info("Applying changelog [$changelogName]...")
  }

  void afterMigrations(Database Database) {
    logger.info("Migrations are done.")
  }
}
