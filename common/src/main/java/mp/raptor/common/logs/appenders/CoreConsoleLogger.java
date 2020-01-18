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

package mp.raptor.common.logs.appenders;


import mp.raptor.common.logs.LogFormatter;

import java.util.ResourceBundle;


/**
 * @author Hassan Nazar, hassenasse @ github (2019)
 */
public class CoreConsoleLogger implements System.Logger {


  @Override
  public String getName() {
    return CoreConsoleLogger.class.getSimpleName();
  }

  @Override
  public boolean isLoggable(final Level level) {
    return level == Level.INFO;
  }

  @Override
  public void log(final Level level,
                  final ResourceBundle bundle,
                  final String msg,
                  final Throwable thrown) {
    System.out.print(LogFormatter.format(msg, thrown, level));
  }

  @Override
  public void log(final Level level,
                  final ResourceBundle bundle,
                  final String format,
                  final Object... params) {
    System.out.print(LogFormatter.format(format, null, level));
  }
}
