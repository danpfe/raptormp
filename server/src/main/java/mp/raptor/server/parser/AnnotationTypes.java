package mp.raptor.server.parser;

/**
 * Purpose:
 * Aggregation of annotations that we want to use for filtering
 * off classes extracted by Jandex.
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public enum AnnotationTypes {
  SERVLET(new String[]{
      "javax.servlet.annotation.WebServlet"
  });

  private final String[] annotationDefinitions;

  AnnotationTypes (final String[] s) {
    annotationDefinitions = s;
  }

  public String[] getAnnotationDefinitions () {
    return annotationDefinitions;
  }
}
