package fr.marcwrobel.gcli

import java.time.Duration
import java.time.LocalDateTime

import static java.time.LocalDateTime.now

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
    cli.d longOpt: 'delay', args: 1, "Delay, specified as an ISO-8601 duration (default = none)."
  }

  LocalDateTime from() {
    def from = options.f ? LocalDateTime.parse(options.f as String) : step().previous(now())

    from = step().adjust(from)
    def delay = delay()
    if(delay) {
      from = from - delay
    }

    return from
  }

  LocalDateTime to() {
    def to = options.t ? LocalDateTime.parse(options.t as String) : now()

    to = step().adjust(to)
    def delay = delay()
    if(delay) {
      to = to - delay
    }

    return to
  }

  Step step() {
    options.s ? Step.valueOf((options.s as String).toUpperCase()) : Step.HOURLY
  }

  Duration delay() {
    options.d ? Duration.parse(options.d as String) : null
  }
}
