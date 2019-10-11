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

package mp.raptor.server;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import mp.raptor.common.RegisterableComponent;
import mp.raptor.common.ServletContainer;

/**
 * Utility class to start the servlet container and register deployable components (i.e. Servlets, Filters or Listeners).
 *
 * @author <a href="mailto:daniel@pfeifer.io">Daniel Pfeifer</a>
 */
public class UndertowServletContainer implements ServletContainer {
  private Undertow undertow;

  /**
   * Starts the servlet container.
   */
  @Override
  public void start() {
    final var port = 8080;
    undertow = Undertow.builder().addHttpListener(port, "localhost", new HttpHandler() {
      @Override
      public void handleRequest(final HttpServerExchange httpServerExchange) throws Exception {
        httpServerExchange.getResponseSender().send("Welcome to RaptorMP!");
      }
    }).build();

    undertow.start();
  }

  /**
   * Tears down the servlet container.
   */
  @Override
  public void stop() {
    undertow.stop();
  }

  /**
   * Registers a component in the servlet container.
   *
   * @param registerableComponent the component to register.
   */
  @Override
  public void registerComponent(final RegisterableComponent registerableComponent) {
    // TODO
  }
}
