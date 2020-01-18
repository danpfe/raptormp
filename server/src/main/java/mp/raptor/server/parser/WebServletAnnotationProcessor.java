package mp.raptor.server.parser;

import javax.servlet.annotation.WebServlet;
import java.lang.annotation.Annotation;

import static java.lang.System.Logger.Level.ERROR;

/**
 * Purpose:
 * Processes a WebServlet annotation.
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class WebServletAnnotationProcessor {
  private static final System.Logger LOGGER = System.getLogger(WebServletAnnotationProcessor.class.getSimpleName());

  private WebServletAnnotationProcessor () {
  }

  /**
   * Given a class location (package structure), loads a WebServlet annotation and returns
   * it to the caller.
   *
   * @param classLocation dot separated class URI
   * @return Webservlet annotation with detail info
   */
  public static WebServlet getServletAnnotation (final String classLocation) {
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
