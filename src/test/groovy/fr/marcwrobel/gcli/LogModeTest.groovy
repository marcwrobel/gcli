package fr.marcwrobel.gcli

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class LogModeTest {

  @Test
  void safeValueOfIsCaseInsensitive() {
    assertEquals(LogMode.CONSOLE, LogMode.safeValueOf(LogMode.CONSOLE.name().toLowerCase()))
  }

  @Test
  void safeValueOfFallbackToDefaultValue() {
    assertEquals(LogMode.DEFAULT, LogMode.safeValueOf(null))
  }
}
