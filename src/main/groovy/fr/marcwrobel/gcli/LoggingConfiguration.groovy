package fr.marcwrobel.gcli

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import org.slf4j.LoggerFactory

/**
 * Basic configuration for a groovy script with logging capabilities.
 */
class LoggingConfiguration extends Configuration {

  LoggingConfiguration(CliTexts texts) {
    super(texts)
    cli.lm longOpt: 'log-mode', args: 1, "Log mode. Possible values are : ${LogMode.displayNames} (default: ${LogMode.DEFAULT.displayName})."
    cli.ll longOpt: 'log-level', args: 1, "Log level (default: info)."
    cli.ld longOpt: 'log-directory', args: 1, 'Log directory (for "file" or "all" log modes) (default: the current directory).'
  }

  @Override
  boolean parse(String[] args) {
    if (!super.parse(args)) {
      return false
    }

    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory()
    Logger rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME)
    rootLogger.detachAndStopAllAppenders()
    rootLogger.level = logLevel

    def mode = logMode
    if (mode != LogMode.NONE) {
      if (mode == LogMode.CONSOLE) {
        PatternLayoutEncoder pl = new PatternLayoutEncoder()
        pl.setContext(lc)
        pl.setPattern('%msg [%level] %n')
        pl.start()

        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>()
        appender.setContext(lc)
        appender.setName('console')
        appender.setEncoder(pl)
        appender.setTarget('System.out')
        appender.start()
        rootLogger.addAppender(appender)

      } else if (mode == LogMode.FILE) {
        PatternLayoutEncoder pl = new PatternLayoutEncoder()
        pl.setContext(lc)
        pl.setPattern('%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n')
        pl.start()

        File logDirectory = logDirectory
        File logFile = new File(logDirectory, "${texts.name()}-${timestamp}.log")

        FileAppender<ILoggingEvent> fileAppender = new FileAppender<>()
        fileAppender.setContext(lc)
        fileAppender.setName('file')
        fileAppender.setFile(logFile.absolutePath)
        fileAppender.setEncoder(pl)
        fileAppender.start()
        rootLogger.addAppender(fileAppender)
      }
    }

    return true
  }

  final LogMode getLogMode() {
    return LogMode.safeValueOf(options.lm as String)
  }

  final Level getLogLevel() {
    return options.ll ? Level.toLevel(options.ll.toString(), Level.INFO) : Level.INFO
  }

  final File getLogDirectory() {
    return new File(options.ld ?: '.')
  }
}
