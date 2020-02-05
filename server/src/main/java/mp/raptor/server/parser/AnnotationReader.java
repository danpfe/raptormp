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

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.jboss.jandex.ClassInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import static java.lang.String.format;
import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.ERROR;

/**
 * Purpose:
 * Finds a jandex index file and fetches all the class-info
 * data.
 *
 * @author Hassan Nazar
 */
public class AnnotationReader {

  private static final System.Logger LOGGER = System.getLogger(AnnotationReader.class.getSimpleName());
  private static Collection<ClassInfo> annotatedClasses = new ArrayList<>();

  /**
   * Given an annotation type, we fetch and filter all annotated classes that are
   * found in the index file.
   *
   * @param type of Annotations aggregation we are interested in.
   * @return Collection of class-information.
   */
  public Collection<ClassInfo> readAnnotations(final AnnotationTypes type) {
    final AnnotationParser parser = new AnnotationParser();
    if (annotatedClasses.isEmpty()) {
      // Load Jandex index file
      LOGGER.log(DEBUG, "Scanning for jandex .idx files in classpath.");
      var indexPaths = new ArrayList<String>();
      try (ScanResult scanResult = new ClassGraph().whitelistPaths("").scan()) {
        indexPaths = (ArrayList<String>) scanResult.getResourcesWithExtension("idx").getPaths();
        LOGGER.log(DEBUG, format("Found %d index files in classpath", indexPaths.size()));
      }

      // Locate and load jandex .idx file
      LOGGER.log(DEBUG, "Attempting to load jandex files");
      for (final String indexPath : indexPaths) {
        LOGGER.log(DEBUG, format("Reading jandex file at location: %s", indexPath));
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(indexPath)) {

          // Cache parsed class-names to a pool
          annotatedClasses = parser.parseClassInformation(inputStream);

        } catch (final FileNotFoundException e) {
          LOGGER.log(ERROR, "Jandex file not found, please make sure it exists in the classpath", e);
        } catch (final IOException e) {
          LOGGER.log(ERROR, "Failed to load index file.", e);
        }
      }
    }

    // Filter and Return parsed pool to caller
    return parser.filterClassesOfType(annotatedClasses, type);
  }


}
