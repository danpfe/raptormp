package mp.raptor.server.parser;

import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.IndexReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static java.lang.System.Logger.Level.DEBUG;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 */
public class AnnotationParser {

  private static final System.Logger LOGGER = System.getLogger(AnnotationParser.class.getSimpleName());

  static Collection<ClassInfo> parseClassInformation (final InputStream inputStream) throws IOException {
    // Parse Jandex file
    LOGGER.log(DEBUG, "Attempting to parse jandex file.");
    final var indexReader = new IndexReader(inputStream);
    final var index = indexReader.read();
    return index.getKnownClasses();
  }

}
