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
 * Defines a component which is capable of being registered to an
 * application container.
 */
public interface RegisterableComponent<T> {

  /**
   * Get the registerable component.
   *
   * @return component that can be registered to the container.
   */
  T getComponent();

  /**
   * Set a registerable component.
   *
   * @param component component that can be registered to the container.
   */
  void setComponent(T component);
}
