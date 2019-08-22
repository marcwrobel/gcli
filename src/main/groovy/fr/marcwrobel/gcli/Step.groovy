package fr.marcwrobel.gcli

import java.time.Duration
import java.time.LocalDateTime

final enum Step {

  DAILY("P1D"){
    @Override
    LocalDateTime adjust(LocalDateTime dt) {
      dt.withHour(0).withMinute(0).withSecond(0).withNano(0)
    }
  },
  HOURLY("PT1H"){
    @Override
    LocalDateTime adjust(LocalDateTime dt) {
      dt.withMinute(0).withSecond(0).withNano(0)
    }
  },
  MINUTELY("PT1M"){
    @Override
    LocalDateTime adjust(LocalDateTime dt) {
      dt.withSecond(0).withNano(0)
    }
  }

  final Duration duration

  Step(String iso8601Duration) {
    this.duration = Duration.parse(iso8601Duration)
  }

  abstract LocalDateTime adjust(LocalDateTime dt)

  LocalDateTime next(LocalDateTime dt) {
    dt + duration
  }

  LocalDateTime previous(LocalDateTime dt) {
    dt - duration
  }
}
