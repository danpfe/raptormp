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
