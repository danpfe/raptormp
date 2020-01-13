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

package mp.raptor.common;

/**
 * Contract for ServletContainer.
 *
 * @author <a href="mailto:daniel@pfeifer.io">Daniel Pfeifer</a>
 */
public interface ServletContainer {
  /**
   * Starts the server.
   */
  void start ();

  /**
   * Stops the server.
   */
  void stop ();

  /**
   * Registers a component (like a Servlet, Listener or Filter).
   *
   * @param registerableComponent the component to register.
   */
  void registerComponent (RegisterableComponent registerableComponent);
}
