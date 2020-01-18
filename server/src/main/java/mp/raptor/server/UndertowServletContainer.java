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
import mp.raptor.server.component.RegisterableServletController;

import javax.servlet.ServletException;
import java.util.stream.Collectors;

import static java.lang.System.Logger.Level.*;

/**
 * Utility class to start the servlet container and register deployable components (i.e. Servlets, Filters or Listeners).
 *
 * @author <a href="mailto:daniel@pfeifer.io">Daniel Pfeifer</a>
 */
public class UndertowServletContainer implements ServletContainer {
  private static final System.Logger LOGGER = System.getLogger(UndertowServletContainer.class.getSimpleName());
  private final RegisterableServletController componentController = new RegisterableServletController();
  private Undertow undertow;

  /**
   * Starts the servlet container.
   */
  @Override
  public void start () {
    try {
      final var port = 8080;
      // Servlets parsed and ready to be added at this point.
      componentController.registerComponents();
      final var servlets = componentController.getRegisterables();
      final var servletInfoList = servlets.stream()
          .map(RegisterableComponent::getComponent)
          .filter(ServletInfo.class::isInstance)
          .map(ServletInfo.class::cast)
          .collect(Collectors.toList());

      if (servletInfoList.isEmpty()) {
        LOGGER.log(INFO, "No servlets found to deploy...");
      } else {
        // Create the servlet deployment manifest with the provided servlets
        LOGGER.log(INFO, "Attempting to deploy registered servlets");
      }

      final DeploymentInfo deploymentInfo = Servlets.deployment()
          .setClassLoader(UndertowServletContainer.class.getClassLoader())
          .setContextPath("/")
          .setDeploymentName("RaptorMP")
          .addServlets(servletInfoList);
      // Assign to deployment manager and get HTTPHandler
      final DeploymentManager manager = Servlets.defaultContainer().addDeployment(deploymentInfo);
      manager.deploy();
      final PathHandler path = Handlers.path(Handlers.redirect("/"))
          .addPrefixPath("/", manager.start());

      // Bootstrap the server
      LOGGER.log(DEBUG, "Bootstrapping RaptorMP");
      undertow = Undertow.builder()
          .addHttpListener(port, "localhost")
          .setHandler(path)
          .build();
      undertow.start();
    } catch (final ServletException e) {
      LOGGER.log(ERROR, "Servlet container failed to start.", e);
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
    // Intentionally left empty
  }

}
