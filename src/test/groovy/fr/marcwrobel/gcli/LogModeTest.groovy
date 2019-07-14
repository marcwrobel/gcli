package fr.marcwrobel.gcli

import org.junit.Assert
import org.junit.Test

class LogModeTest {

  @Test
  void safeValueOfIsCaseInsensitive() {
    Assert.assertEquals(LogMode.CONSOLE, LogMode.safeValueOf(LogMode.CONSOLE.name().toLowerCase()))
  }

  @Test
  void safeValueOfFallbackToDefaultValue() {
    Assert.assertEquals(LogMode.DEFAULT, LogMode.safeValueOf(null))
  }
}
