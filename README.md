[![Maven Central](https://img.shields.io/maven-central/v/fr.marcwrobel/gcli.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22fr.marcwrobel%22%20AND%20a:%22gcli%22)

> **Warning**
> This project is discontinued: I do not use it anymore and I have lost interest in it. Feel free to fork !

GCLI aims to make scripting with Groovy easier by providing opinionated utilities related to common concerns like
logging or database accesses.

## Requirements

Any script that uses gcli must use [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
and [Groovy 2.5](https://groovy-lang.org/install.html) or later.

## Use it !

Here is a sample script that makes use of logging and database access capabilities :

```groovy
#!/usr/bin/env groovy

import fr.marcwrobel.gcli.ScriptCliTexts
import groovy.sql.Sql
import groovy.util.logging.Slf4j

@Grab(group = 'fr.marcwrobel', module = 'gcli', version = '0.0.5')
@Grab(group = 'ch.qos.logback', module = 'logback-classic', version = '1.2.11')

@GrabConfig(systemClassLoader = true)
@Grab(group = 'org.postgresql', module = 'postgresql', version = '42.3.3')

final class CommandConfiguration extends DatabaseConfiguration {
  CommandConfiguration(Class<?> scriptClass) {
    super(new ScriptCliTexts(scriptClass))
  }
}

@Slf4j
class Command {

  private final CommandConfiguration config

  Command(CommandConfiguration config) {
    this.config = config
  }

  int execute() {
    try {
      config.withSql { Sql sql ->
        // do something
        return 0
      }

    } catch (Exception e) {
      log.error('Command failed : {}', e.message, e)
      throw e
    }

    log.info('Command succeeded.')
    return 0
  }
}

CommandConfiguration config = new CommandConfiguration(this.class)
if (!config.parse(args)) {
  System.exit(1)
} else if (config.showHelp) {
  config.usage()
  System.exit(0)
}

System.exit(new Command(config).execute())
```

## Releases

Take a look at the [changelog on GitHub](https://github.com/marcwrobel/gcli/releases).

## Need help ?

Read the [javadoc](src/main/java/fr/marcwrobel/gcli).

Raise an [issue](https://github.com/marcwrobel/gcli/issues?sort=created&direction=desc&state=open).

Email me at [marc.wrobel@gmail.com](mailto:marc.wrobel@gmail.com).
