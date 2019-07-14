package fr.marcwrobel.gcli

import ch.qos.logback.classic.Level
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.junit.Assert.*

class LoggingConfigurationTest {

  private static final CliTexts TEXTS = new SimpleCliTexts('test')

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder()

  @Test
  void parsingTest() {
    LoggingConfiguration configuration = new LoggingConfiguration(TEXTS)
    assertTrue(configuration.parse(['-lm', 'file', '-ll', 'debug', '-ld', tempFolder.root.absolutePath] as String[]))

    assertEquals(LogMode.FILE, configuration.logMode)
    assertEquals(Level.DEBUG, configuration.logLevel)
    assertEquals(tempFolder.root, configuration.logDirectory)
  }

  @Test
  void invalidArgumentTest() {
    LoggingConfiguration configuration = new LoggingConfiguration(TEXTS)
    assertFalse(configuration.parse(['-i'] as String[]))
  }

}
