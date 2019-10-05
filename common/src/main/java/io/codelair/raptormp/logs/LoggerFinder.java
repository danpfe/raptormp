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

package io.codelair.raptormp.logs;

import io.codelair.raptormp.logs.appenders.CoreConsoleLogger;

/**
 * @author Hassan Nazar, hassenasse @ github (2019)
 */
public class LoggerFinder extends System.LoggerFinder {

  @Override
  public System.Logger getLogger(final String name, final Module module) {
    return new CoreConsoleLogger();
  }
}
