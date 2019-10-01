package io.codelair.raptormp.logs;

import io.codelair.raptormp.logs.appenders.LogUtilConsoleAppender;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Hassan Nazar, hassenasse @ github (2019)
 */
public class LogConfigurator
{

  public static void init()
  {

    // Reset logging config
    LogManager.getLogManager().reset();

    Logger rootLogger = LogManager.getLogManager().getLogger( "" );

    LogUtilConsoleAppender handler = new LogUtilConsoleAppender();

    rootLogger.addHandler( handler );

  }

}
