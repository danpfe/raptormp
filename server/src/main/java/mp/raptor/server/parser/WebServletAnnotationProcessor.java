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

import javax.servlet.annotation.WebServlet;
import java.lang.annotation.Annotation;

import static java.lang.System.Logger.Level.ERROR;

/**
 * Purpose:
 * Processes a WebServlet annotation.
 *
 * @author Hassan Nazar
 */
public final class WebServletAnnotationProcessor {
  private static final System.Logger LOGGER = System.getLogger(WebServletAnnotationProcessor.class.getSimpleName());

  private WebServletAnnotationProcessor() {
  }

  /**
   * Given a class location (package structure), loads a WebServlet annotation and returns
   * it to the caller.
   *
   * @param classLocation dot separated class URI
   * @return Webservlet annotation with detail info
   */
  public static WebServlet getServletAnnotation(final String classLocation) {
    try {
      final var clazz = Thread.currentThread().getContextClassLoader().loadClass(classLocation);
      final var annotations = clazz.getAnnotations();

      for (final Annotation annotation : annotations) {
        if (annotations[0] instanceof WebServlet) {
          return (WebServlet) annotation;
        }
      }
    } catch (final ClassNotFoundException e) {
      LOGGER.log(ERROR, "Class not found for fetching annotations", e);
    }

    return null;
  }

}
