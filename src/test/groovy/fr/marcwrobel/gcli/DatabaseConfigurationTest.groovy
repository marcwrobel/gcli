package fr.marcwrobel.gcli

import groovy.sql.Sql
import org.junit.jupiter.api.Test

import java.sql.SQLException

import static org.junit.jupiter.api.Assertions.*

class DatabaseConfigurationTest {

  private static final CliTexts TEXTS = new SimpleCliTexts('test')

  @Test
  void parsingTest() {
    DatabaseConfiguration configuration = new DatabaseConfiguration(TEXTS)

    assertTrue(configuration.parse(['-db', 'toto', '-du', 'titi'] as String[]))
    assertEquals('toto', configuration.databaseName)
    assertEquals('titi', configuration.databaseUser)

    assertThrows(SQLException.class, { ->
      configuration.withSql { Sql sql ->
        println('Hello world !')
      }
    })
  }

  @Test
  void invalidArgumentTest() {
    DatabaseConfiguration configuration = new DatabaseConfiguration(TEXTS)
    assertFalse(configuration.parse(['-i'] as String[]))
  }

}
