package io.codelair.raptormp.logs;

import io.codelair.raptormp.logs.appenders.CoreConsoleLogger;

/**
 * @author Hassan Nazar, hassenasse @ github (2019)
 */
public class LoggerFinder extends System.LoggerFinder
{

  @Override
  public System.Logger getLogger( String name, Module module )
  {
    return new CoreConsoleLogger();
  }
}
