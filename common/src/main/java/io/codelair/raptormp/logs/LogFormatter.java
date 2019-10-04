package io.codelair.raptormp.logs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import static java.lang.System.Logger.Level;

/**
 * RaptorMP logs formatter, used throughout the server
 * to format log entries according to a pre-defined style.
 * Responsible for converting and formatting the data in a log event.
 * <p>
 *
 * @author Hassan Nazar, hassenasse @ github (2019)
 * @see also CoreConsoleLogger LoggerFinder
 */
public class LogFormatter
{
  private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss:SSS" );

  public static String format( String msg, Throwable thrown, Level level )
  {

    ZonedDateTime zoneTime = Instant.now()
        .atZone( ZoneId.systemDefault() );

    String message = msg;
    String throwable = "";

    // Print throwable, if existing
    if ( thrown != null )
    {

      StringWriter writer = new StringWriter();
      PrintWriter printWriter = new PrintWriter( writer );
      printWriter.println();
      thrown.printStackTrace( printWriter );
      printWriter.close();
      throwable = writer.toString();

    }

    return zoneTime.format( dateTimeFormat ) + " "
        + level
        + " :: "
        + message + throwable + "\n";
  }
}
