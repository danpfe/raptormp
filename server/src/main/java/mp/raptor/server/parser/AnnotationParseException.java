package mp.raptor.server.parser;

/**
 * Purpose:
 * Parse exception, thrown if an expected servlet is not found from the
 * WebServletAnnotationProcessor.
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class AnnotationParseException extends Exception {
  private static final long serialVersionUID = -4234989985006571896L;

  public AnnotationParseException (final String message) {
    super(message);
  }
}
