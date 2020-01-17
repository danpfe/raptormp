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

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletInfo;
import mp.raptor.common.RegisterableComponent;
import mp.raptor.common.ServletContainer;
import mp.raptor.server.component.RegisterableComponentNotifier;
import mp.raptor.server.component.RegisterableServletObserver;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to start the servlet container and register deployable components (i.e. Servlets, Filters or Listeners).
 *
 * @author <a href="mailto:daniel@pfeifer.io">Daniel Pfeifer</a>
 */
public class UndertowServletContainer implements ServletContainer {
  public static final RegisterableComponentNotifier notifier = new RegisterableComponentNotifier();
  private final Undertow.Builder undertowBuilder;
  private Undertow undertow;
  private List<ServletInfo> servletInfoList;

  public UndertowServletContainer () {
    final var port = 8080;
    undertowBuilder = Undertow.builder()
        .addHttpListener(port, "localhost", httpServerExchange -> httpServerExchange.getResponseSender().send("Welcome to RaptorMP!"));

    // Create the component-notifier
    final RegisterableServletObserver observer = new RegisterableServletObserver(this);
    notifier.register(observer);
  }

  /**
   * Starts the servlet container.
   */
  @Override
  public void start () {
    final DeploymentInfo servletBuilder = Servlets.deployment()
        .setClassLoader(UndertowServletContainer.class.getClassLoader())
        .setContextPath("/")
        .setDeploymentName("RaptorMP")
        .addServlets(servletInfoList);

    try {
      final DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
      manager.deploy();
      final PathHandler path = Handlers.path(Handlers.redirect("/")).addPrefixPath("/", manager.start());

      // Bootstrap the server
      undertow = undertowBuilder
          .setHandler(path)
          .build();

      undertow.start();
    } catch (final ServletException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tears down the servlet container.
   */
  @Override
  public void stop () {
    undertow.stop();
  }

  /**
   * Registers a component in the servlet container.
   *
   * @param registerableComponent the component to register.
   */
  @Override
  public void registerComponent (final RegisterableComponent registerableComponent) {
    // Serlets parsed and ready to be added at this point.
    servletInfoList = new ArrayList<>();
    start();
  }

}
