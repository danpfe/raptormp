/*
 * Copyright 2019 RedBridge Technology AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mp.raptor.common.logs;

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
public final class LogFormatter {
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

  private LogFormatter() {
    // Intentionally left blank
  }

  /**
   * Applies a pre-defined format to logging data which is
   * passed in.
   *
   * @param msg    log message
   * @param thrown optional throwable
   * @param level  log level
   * @return formatted log-string, passed on to appender
   */
  public static String format(final String msg, final Throwable thrown, final Level level) {

    final ZonedDateTime zoneTime = Instant.now()
        .atZone(ZoneId.systemDefault());

    String throwable = "";

    // Print throwable, if existing
    if (thrown != null) {

      final StringWriter writer = new StringWriter();
      final PrintWriter printWriter = new PrintWriter(writer);
      printWriter.println();
      thrown.printStackTrace(printWriter);
      printWriter.close();
      throwable = writer.toString();

    }

    return zoneTime.format(DATE_TIME_FORMATTER) + " "
        + level
        + " :: "
        + msg + throwable + "\n";
  }
}
