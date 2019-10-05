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

package io.codelair.raptormp;

import io.codelair.raptormp.server.UndertowServletContainer;
import java.lang.management.ManagementFactory;
import org.eclipse.microprofile.config.Config;

/**
 * Boots RaptorMP.
 */
public final class Runner {
  /**
   * Main entry point.
   *
   * @param args command line arguments.
   */
  public static void main(final String[] args) {
    new Runner().startWithConfig(null);
  }

  private void startWithConfig(final Config config) {
    final var startTime = System.currentTimeMillis();
    try {
      System.out.println("RaptorMP is starting ...");
      final var servletContainer = new UndertowServletContainer();
      servletContainer.start();
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        System.out.println("RaptorMP is cleanly shutting down ...");
        servletContainer.stop();
        System.out.println("RaptorMP has clawed it's guts out and is now dead; Bye!");
      }));
    } finally {
      final var jvmUpTime = ManagementFactory.getRuntimeMXBean().getUptime();
      final var codeTime = System.currentTimeMillis() - startTime;
      System.out.println("RaptorMP started in " + codeTime + "ms. Total JVM-runtime was " + jvmUpTime + "ms!");
    }
  }

  private Runner() {
    // intentionally left empty
  }
}
