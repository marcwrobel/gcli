package fr.marcwrobel.gcli

import groovy.sql.Sql
import org.junit.Test

import java.sql.SQLException

import static org.junit.Assert.*

class DatabaseConfigurationTest {

  private static final CliTexts TEXTS = new SimpleCliTexts('test')

  @Test(expected = SQLException.class)
  void parsingTest() {
    DatabaseConfiguration configuration = new DatabaseConfiguration(TEXTS)
    assertTrue(configuration.parse(['-db', 'toto', '-du', 'titi'] as String[]))

    assertEquals('toto', configuration.databaseName)
    assertEquals('titi', configuration.databaseUser)

    configuration.withSql { Sql sql ->
      println('Hello world !')
    }
  }

  @Test
  void invalidArgumentTest() {
    DatabaseConfiguration configuration = new DatabaseConfiguration(TEXTS)
    assertFalse(configuration.parse(['-i'] as String[]))
  }

}
