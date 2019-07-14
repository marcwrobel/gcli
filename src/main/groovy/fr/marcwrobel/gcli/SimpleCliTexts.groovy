package fr.marcwrobel.gcli

class SimpleCliTexts implements CliTexts {

  private final String cliName
  private final List<String> args
  private final String footer

  SimpleCliTexts(String cliName, List<String> args, String footer) {
    this.cliName = Objects.requireNonNull(cliName)
    this.args = Objects.requireNonNull(args)
    this.footer = footer
  }

  SimpleCliTexts(String cliName, String footer) {
    this(cliName, Collections.emptyList(), footer)
  }

  SimpleCliTexts(String cliName) {
    this(cliName, null)
  }

  private String getArgsList() {
    return args.collect { '<' + it + '>' }.join(' ')
  }

  @Override
  String name() {
    return cliName
  }

  @Override
  List<String> args() {
    return new ArrayList<>(args)
  }

  @Override
  String usage() {
    def usage = cliName + ' [options]'

    if (args) {
      usage += ' ' + getArgsList()
    }

    return usage
  }

  String footer() {
    return footer
  }
}
