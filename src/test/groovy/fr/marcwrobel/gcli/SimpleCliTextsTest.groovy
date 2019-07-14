package fr.marcwrobel.gcli

import org.junit.Test

import static org.junit.Assert.assertEquals

class SimpleCliTextsTest {

  private static final String NAME = 'cli'
  private static final String FOOTER = 'footer'

  private static final String ARG1 = 'URL'
  private static final String ARG2 = 'FILE'
  private static final List<String> ARGS = [ARG1, ARG2]

  @Test(expected = NullPointerException.class)
  void cliNameIsMandatory() {
    new SimpleCliTexts(null)
  }

  @Test
  void generateUsage() {
    SimpleCliTexts texts = new SimpleCliTexts(NAME, ARGS, FOOTER)
    assertEquals(NAME, texts.name())
    assertEquals(ARGS, texts.args())
    assertEquals(FOOTER, texts.footer())
    assertEquals("${NAME} [options] <${ARG1}> <${ARG2}>".toString(), texts.usage())
  }
}
