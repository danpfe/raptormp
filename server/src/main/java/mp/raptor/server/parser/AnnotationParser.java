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

import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.System.Logger.Level.DEBUG;

/**
 * Purpose:
 * Annotation parser and filtering utilites, used by AnnotationReader.
 *
 * @author Hassan Nazar
 */
public class AnnotationParser {

  private static final System.Logger LOGGER = System.getLogger(AnnotationParser.class.getSimpleName());

  Collection<ClassInfo> parseClassInformation (final InputStream inputStream) throws IOException {
    // Parse Jandex file
    LOGGER.log(DEBUG, "Attempting to parse jandex file.");
    final var indexReader = new IndexReader(inputStream);
    final var index = indexReader.read();
    return index.getKnownClasses();
  }

  List<ClassInfo> filterClassesOfType (final Collection<ClassInfo> annotatedClasses, final AnnotationTypes type) {
    final List<ClassInfo> filteredClasses = new ArrayList<>();
    for (final ClassInfo classInfo : annotatedClasses) {
      for (final String annotation : type.getAnnotationDefinitions()) {
        if (classInfo.annotations().get(DotName.createSimple(annotation)) != null) {
          filteredClasses.add(classInfo);
        }
      }
    }

    return filteredClasses;
  }
}
