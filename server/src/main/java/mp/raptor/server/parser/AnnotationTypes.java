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
package mp.raptor.server.parser;

/**
 * Purpose:
 * Aggregation of annotations that we want to use for filtering
 * off classes extracted by Jandex.
 *
 * @author Hassan Nazar
 */
public enum AnnotationTypes {
  /**
   * Defines a grouping of annotations called servlet.
   * Used when parsing index files for registerable
   * servlets to the container.
   */
  SERVLET(new String[]{
      "javax.servlet.annotation.WebServlet"
  });

  private final String[] annotationDefinitions;

  /**
   * Assigns package structures for annotation types.
   *
   * @param s list of packages.
   */
  AnnotationTypes(final String[] s) {
    annotationDefinitions = s;
  }

  /**
   * Return a list of definitions for an annotation type.
   *
   * @return list of definition URIs.
   */
  public String[] getAnnotationDefinitions() {
    return annotationDefinitions;
  }
}
