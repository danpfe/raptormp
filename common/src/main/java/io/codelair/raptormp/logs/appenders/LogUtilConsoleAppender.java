package io.codelair.raptormp.logs.appenders;

import io.codelair.raptormp.logs.LogFormatter;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * Also called Handlers.
 * Responsible for recording log events to System-Out.
 *
 * @author Hassan Nazar, hassenasse @ github (2019)
 * @see also     LogFormatter
 */
public class LogUtilConsoleAppender extends StreamHandler
{

  /**
   * Constructor for <tt>LogUtilConsoleAppender</tt>
   */
  public LogUtilConsoleAppender()
  {

    //Default log-level
    setLevel( Level.INFO );
    setFormatter( new LogFormatter() );
    setOutputStream( System.out );

  }

  @Override
  public void publish( LogRecord record )
  {
    super.publish( record );
    // Flushes any buffered messages in the stream to System.out
    flush();
  }

  /**
   * Override <tt>StreamHandler.close</tt> to do a flush but not
   * to close the output stream.  That is, we do <b>not</b>
   * close <tt>System.out</tt>.
   */
  @Override
  public void close()
  {
    flush();
  }

}
