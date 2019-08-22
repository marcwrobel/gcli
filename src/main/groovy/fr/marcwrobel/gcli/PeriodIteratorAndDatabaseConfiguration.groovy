package fr.marcwrobel.gcli

import java.time.LocalDateTime

/**
 * Basic configuration for a groovy script with database capabilities and period iterator
 * capabilities.
 *
 * The following script can be build with it
 */
class PeriodIteratorAndDatabaseConfiguration extends DatabaseConfiguration {

  PeriodIteratorAndDatabaseConfiguration(CliTexts texts) {
    super(texts)
    cli.f longOpt: 'from', args: 1, "start timestamp, formatted as YYYY-MM-DDTHH:mm:ss (default = now - duration)."
    cli.t longOpt: 'to', args: 1, "end timestamp, formatted as YYYY-MM-DDTHH:mm:ss (default = now)."
    cli.s longOpt: 'step', args: 1, "Step's duration, specified as DAILY, HOURLY, MINUTELY (default = HOURLY)."
  }

  LocalDateTime from() {
    step().adjust(options.f ? LocalDateTime.parse(options.f as String) : step().previous(LocalDateTime.now()))
  }

  LocalDateTime to() {
    step().adjust(options.t ? LocalDateTime.parse(options.t as String) : LocalDateTime.now())
  }

  Step step() {
    options.s ? Step.valueOf((options.s as String).toUpperCase()) : Step.HOURLY
  }
}
