package mp.raptor.server.parser;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.jboss.jandex.ClassInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static java.lang.String.format;
import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.ERROR;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 */
public class AnnotationReader {

  private static final System.Logger LOGGER = System.getLogger(AnnotationReader.class.getSimpleName());

  public Optional<Collection<ClassInfo>> readAnnotations () {
    Collection<ClassInfo> annotatedClasses = null;

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
        if (annotatedClasses == null) {
          annotatedClasses = AnnotationParser.parseClassInformation(inputStream);
        } else {
          annotatedClasses.addAll(AnnotationParser.parseClassInformation(inputStream));
        }

      } catch (final FileNotFoundException e) {
        LOGGER.log(ERROR, "Jandex file not found, please make sure it exists in the classpath", e);
      } catch (final IOException e) {
        LOGGER.log(ERROR, "Failed to load index file.", e);
      }
    }

    // Return parsed pool to caller
    return Optional.ofNullable(annotatedClasses);
  }


}
