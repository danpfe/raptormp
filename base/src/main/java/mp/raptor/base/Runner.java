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

package mp.raptor.base;

import mp.raptor.server.UndertowServletContainer;
import org.eclipse.microprofile.config.Config;

import java.lang.management.ManagementFactory;

import static java.lang.System.Logger.Level;

/**
 * Boots RaptorMP.
 */
public final class Runner {

  private static final System.Logger LOGGER = System.getLogger(Runner.class.getSimpleName());

  private Runner () {
    // intentionally left empty
  }

  /**
   * Main entry point.
   *
   * @param args command line arguments.
   */
  public static void main (final String[] args) {
    final var logo = "\n"
        + "_____________________________________________     ______  __________ \n"
        + "___  __ \\__    |__  __ \\__  __/_  __ \\__  __ \\    ___   |/  /__  __ \\\n"
        + "__  /_/ /_  /| |_  /_/ /_  /  _  / / /_  /_/ /    __  /|_/ /__  /_/ /\n"
        + "_  _, _/_  ___ |  ____/_  /   / /_/ /_  _, _/     _  /  / / _  ____/ \n"
        + "/_/ |_| /_/  |_/_/     /_/    \\____/ /_/ |_|      /_/  /_/  /_/\n"
        + "A fast, light and customizable Eclipse MicroProfile™-middleware.\n";

    LOGGER.log(Level.INFO, logo);

    new Runner().startWithConfig(null);
  }

  private void startWithConfig (final Config config) {
    final var startTime = System.currentTimeMillis();

    try {
      LOGGER.log(Level.INFO, "RaptorMP is starting ...");
      final var servletContainer = new UndertowServletContainer();
      servletContainer.start();

      // Add shutdown hook
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        LOGGER.log(Level.INFO, "RaptorMP is cleanly shutting down ...");
        servletContainer.stop();
        LOGGER.log(Level.INFO, "RaptorMP has clawed it's guts out and is now dead; Bye!");
      }));

    } finally {
      final var jvmUpTime = ManagementFactory.getRuntimeMXBean().getUptime();
      final var codeTime = System.currentTimeMillis() - startTime;
      LOGGER.log(Level.INFO, "RaptorMP started in " + codeTime + "ms. Total JVM-runtime was " + jvmUpTime + "ms!");
    }
  }
}
