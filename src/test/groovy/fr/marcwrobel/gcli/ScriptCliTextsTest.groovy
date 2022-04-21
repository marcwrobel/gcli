package fr.marcwrobel.gcli

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class ScriptCliTextsTest {

  private static final String SCRIPT_NAME = 'test-classes'
  private static final String FOOTER = 'footer'

  private static final String ARG1 = 'URL'
  private static final String ARG2 = 'FILE'
  private static final List<String> ARGS = [ARG1, ARG2]

  @Test
  void scriptClassIsMandatory() {
    assertThrows(NullPointerException.class, { -> new ScriptCliTexts(null) })
  }

  @Test
  void generateUsage() {
    ScriptCliTexts texts = new ScriptCliTexts(this.class, ARGS, FOOTER)

    assertEquals(SCRIPT_NAME, texts.name())
    assertEquals(ARGS, texts.args())
    assertEquals(FOOTER, texts.footer())
    assertEquals("${SCRIPT_NAME} [options] <${ARG1}> <${ARG2}>".toString(), texts.usage())
  }
}
