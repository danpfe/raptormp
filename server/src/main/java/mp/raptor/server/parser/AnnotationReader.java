package mp.raptor.server.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 */
public class AnnotationReader {

  public void readAnnotations() throws FileNotFoundException {
    // Load Jandex index file

    try (FileInputStream indexFile = new FileInputStream("index.idx")) {

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
