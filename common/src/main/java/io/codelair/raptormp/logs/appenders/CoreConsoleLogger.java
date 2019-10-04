package io.codelair.raptormp.logs.appenders;

import io.codelair.raptormp.logs.LogFormatter;

import java.util.ResourceBundle;


/**
 * @author Hassan Nazar, hassenasse @ github (2019)
 */
public class CoreConsoleLogger implements System.Logger
{


  @Override
  public String getName()
  {
    return CoreConsoleLogger.class.getSimpleName();
  }

  @Override
  public boolean isLoggable( Level level )
  {
    return level == Level.INFO;
  }

  @Override
  public void log( Level level, ResourceBundle bundle, String msg, Throwable thrown )
  {
    System.out.print( LogFormatter.format( msg, thrown, level ) );
  }

  @Override
  public void log( Level level, ResourceBundle bundle, String format, Object... params )
  {
    System.out.print( LogFormatter.format( format, null, level ) );
  }
}
