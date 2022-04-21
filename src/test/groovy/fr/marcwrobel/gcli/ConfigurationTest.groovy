package fr.marcwrobel.gcli

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class ConfigurationTest {

  private static final CliTexts TEXTS = new SimpleCliTexts('test')

  @Test
  void noArgTest() {
    Configuration configuration = new Configuration(TEXTS)

    configuration.parse([] as String[])

    assertFalse(configuration.showHelp)
    configuration.usage()
  }

  @Test
  void helpTest() {
    Configuration configuration = new Configuration(TEXTS)
    configuration.parse(['-h'] as String[])
    assertTrue(configuration.showHelp)
  }

  @Test
  void invalidArgumentTest() {
    Configuration configuration = new Configuration(TEXTS)
    assertFalse(configuration.parse(['-h', '-i'] as String[]))
  }

}
