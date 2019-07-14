package fr.marcwrobel.gcli

final enum LogMode {
  NONE, CONSOLE, FILE

  String getDisplayName() {
    return name().toLowerCase()
  }

  /**
   * The default log mode.
   */
  static final LogMode DEFAULT = CONSOLE

  static String getDisplayNames() {
    return values().collect({ it.displayName }).join(', ')
  }

  /**
   * Similar to {@link #valueOf(java.lang.String)} but case-insensitive.
   *
   * @param name Any String
   * @return the enum constant with the specified name (case-insensitive) or {@link #DEFAULT}.
   */
  static LogMode safeValueOf(String name) {
    for (LogMode mode : values()) {
      if (mode.name().equalsIgnoreCase(name)) {
        return mode
      }
    }

    return DEFAULT
  }
}
