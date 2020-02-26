package fr.marcwrobel.gcli

import groovy.sql.Sql

/**
 * Basic configuration for a groovy script with database capabilities.
 */
class DatabaseConfiguration extends LoggingConfiguration {

  private static final String POSTGRES_DRIVER = 'org.postgresql.Driver'

  private final File passFile = new File(userHome, '.dbpass')
  private final File pgPassFile = new File(userHome, '.pgpass')

  DatabaseConfiguration(CliTexts texts) {
    super(texts)
    cli.dc longOpt: 'db-connection', args: 1, 'Database connection string (default : a postgresql connection URL based on db-name)'
    cli.du longOpt: 'db-user', args: 1, 'Database user name (default : the current user name)'
    cli.dd longOpt: 'db-driver', args: 1, 'Database driver (default : org.postgresql.Driver)'
    cli.db longOpt: 'db-name', args: 1, 'Database name (default : the current user name)'
  }

  def withSql(Closure<Integer> c) {
    def dbUser = databaseUser
    def dbUrl = databaseUrl
    def dbPassword = databasePassword
    def dbDriver = databaseDriver

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
    return options.du ?: user
  }

  final String getDatabaseUrl() {
    String dbUrl = options.dc

    if (!dbUrl) {
      def dbName = databaseName
      def dbUser = databaseUser
      def dbHost = retrieveValueFromPgPass(dbUser, 0, 'localhost')
      def dbPort = retrieveValueFromPgPass(dbUser, 1, '5432')
      dbUrl = "jdbc:postgresql://${dbHost}:${dbPort}/${dbName}"
    }

    return dbUrl
  }

  final String getDatabasePassword() {
    if (databaseDriver == POSTGRES_DRIVER) {
      def dbUser = databaseUser
      return retrieveValueFromPgPass(dbUser, 4, dbUser)
    }

    if(!passFile.exists()) {
      throw new IllegalStateException(passFile + ' must exist !')
    }

    def passFileLines = passFile.readLines()
    if(passFileLines.size() == 0) {
      throw new IllegalStateException(passFile + ' must not be empty !')
    }

    return passFileLines.get(0)
  }

  final String getDatabaseDriver() {
    return options.dd ?: POSTGRES_DRIVER
  }
}
