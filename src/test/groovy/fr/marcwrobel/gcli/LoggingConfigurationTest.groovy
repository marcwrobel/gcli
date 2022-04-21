package fr.marcwrobel.gcli

import ch.qos.logback.classic.Level
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

import static org.junit.jupiter.api.Assertions.*

class LoggingConfigurationTest {

  private static final CliTexts TEXTS = new SimpleCliTexts('test')

  @TempDir
  protected File tempFolder;

  @Test
  void parsingTest() {
    LoggingConfiguration configuration = new LoggingConfiguration(TEXTS)

    assertTrue(configuration.parse(['-lm', 'file', '-ll', 'debug', '-ld', tempFolder.absolutePath] as String[]))
    assertEquals(LogMode.FILE, configuration.logMode)
    assertEquals(Level.DEBUG, configuration.logLevel)
    assertEquals(tempFolder, configuration.logDirectory)
  }

  @Test
  void invalidArgumentTest() {
    LoggingConfiguration configuration = new LoggingConfiguration(TEXTS)

    assertFalse(configuration.parse(['-i'] as String[]))
  }

}
