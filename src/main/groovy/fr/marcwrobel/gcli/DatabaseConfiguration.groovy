package fr.marcwrobel.gcli

import groovy.sql.Sql

/**
 * Basic configuration for a groovy script with database capabilities.
 */
class DatabaseConfiguration extends LoggingConfiguration {

  private final File pgPassFile = new File(userHome, '.pgpass')

  DatabaseConfiguration(CliTexts texts) {
    super(texts)
    cli.db longOpt: 'db-name', args: 1, 'Database name (default : the current user name)'
    cli.du longOpt: 'db-user', args: 1, 'Database user name (default : the current user name)'
  }

  def withSql(Closure<Integer> c) {
    def dbName = databaseName
    def dbUser = databaseUser
    def dbHost = retrieveValueFromPgPass(dbUser, 0, 'localhost')
    def dbPort = retrieveValueFromPgPass(dbUser, 1, '5432')
    def dbUrl = "jdbc:postgresql://${dbHost}:${dbPort}/${dbName}"
    def dbPassword = retrieveValueFromPgPass(dbUser, 4, dbUser)
    def dbDriver = 'org.postgresql.Driver'

    Integer returnValue = 0
    Sql.withInstance(dbUrl, dbUser, dbPassword, dbDriver) { Sql sql ->
      sql.withTransaction {
        returnValue = c.call(sql)
      }
    }

    return returnValue
  }

  private String retrieveValueFromPgPass(String user, int index, String defaultValue) {
    if (pgPassFile.exists()) {
      for (String line : pgPassFile.readLines()) {
        if (line =~ /${user}:[^:].+$/) {
          return line.split(':')[index]
        }
      }
    }

    defaultValue
  }

  final String getDatabaseName() {
    return options.db ?: user
  }

  final String getDatabaseUser() {
    return options.du ?: databaseName
  }
}
