package fr.marcwrobel.gcli


import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Basic configuration for a groovy script.
 */
class Configuration {

  final String user = System.getProperty("user.name")
  final String userHome = System.getProperty("user.home")
  final ZonedDateTime now = ZonedDateTime.now()

  protected final CliTexts texts
  protected final CliBuilder cli

  protected OptionAccessor options

  Configuration(CliTexts texts) {
    this.texts = Objects.requireNonNull(texts)
    cli = new CliBuilder(
      expandArgumentFiles: true,
      stopAtNonOption: false,
      usage: texts.usage(),
      width: 180,
      header: '\nOptions:'
    )

    if (texts.footer()) cli.footer = texts.footer()

    cli.h longOpt: 'help', 'Show usage information'
  }

  boolean parse(String[] args) {
    if (options) {
      throw new IllegalStateException("Configuration already parsed !")
    }

    options = cli.parse(args)
    return options != null
  }

  final String getTimestamp() {
    return now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
  }

  final usage() {
    cli.usage()
  }

  final boolean isShowHelp() {
    options.h
  }
}
