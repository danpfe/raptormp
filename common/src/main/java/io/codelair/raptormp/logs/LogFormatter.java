package io.codelair.raptormp.logs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * RaptorMP logs formatter, used throughout the server
 * to format log entries according to a pre-defined style.
 * Responsible for converting and formatting the data in a log event.
 * <p>
 *
 * @author Hassan Nazar, hassenasse @ github (2019)
 * @see also LogUtilConsoleAppender
 */
public class LogFormatter extends Formatter
{
  private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss:SSS" );

  public String format( LogRecord record )
  {

    ZonedDateTime zoneTime = Instant.ofEpochMilli( record.getMillis() )
        .atZone( ZoneId.systemDefault() );

    String logSource = record.getSourceClassName() != null ? record.getSourceClassName() : record.getLoggerName();
    String message = formatMessage( record );
    String throwable = "";

    // Print throwable, if existing
    if ( record.getThrown() != null )
    {

      StringWriter writer = new StringWriter();
      PrintWriter printWriter = new PrintWriter( writer );
      printWriter.println();
      record.getThrown().printStackTrace( printWriter );
      printWriter.close();
      throwable = writer.toString();

    }

    return zoneTime.format( dateTimeFormat ) + " "
        + record.getLevel().getName()
        + " :: " + logSource + " :: "
        + message + throwable + "\n";
  }
}
