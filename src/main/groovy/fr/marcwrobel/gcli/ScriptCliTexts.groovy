package fr.marcwrobel.gcli

class ScriptCliTexts implements CliTexts {

  private final SimpleCliTexts texts

  ScriptCliTexts(Class<?> scriptClass, List<String> args, String footer) {
    def script = new File(scriptClass.protectionDomain.codeSource.location.path)
    def cliName = script.name.replaceFirst('\\.groovy', '')
    this.texts = new SimpleCliTexts(cliName, args, footer)
  }

  ScriptCliTexts(Class<?> scriptClass, String footer) {
    this(scriptClass, Collections.emptyList(), footer)
  }

  ScriptCliTexts(Class<?> scriptClass) {
    this(scriptClass, null)
  }

  @Override
  String name() {
    return texts.name()
  }

  @Override
  List<String> args() {
    return texts.args()
  }

  @Override
  String usage() {
    return texts.usage()
  }

  @Override
  String footer() {
    return texts.footer()
  }
}
