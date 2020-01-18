package mp.raptor.server.component;

import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.ServletInfo;
import mp.raptor.common.RegisterableComponent;
import mp.raptor.common.RegistrationController;
import mp.raptor.server.parser.AnnotationParseException;
import mp.raptor.server.parser.AnnotationReader;
import mp.raptor.server.parser.AnnotationTypes;
import mp.raptor.server.parser.WebServletAnnotationProcessor;
import org.jboss.jandex.ClassInfo;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.INFO;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public class RegisterableServletController implements RegistrationController<ServletInfo> {
  private static final Logger LOGGER = System.getLogger(RegisterableServletController.class.getSimpleName());

  private final List<RegisterableComponent<ServletInfo>> components;

  public RegisterableServletController () {
    this.components = new ArrayList<>();
  }

  @Override
  public void registerComponents () {
    // Read annotations to get a collection of registerable servlets
    LOGGER.log(DEBUG, "Attempting to register servlets");
    final AnnotationReader annotationReader = new AnnotationReader();
    final var classes = annotationReader.readAnnotations(AnnotationTypes.SERVLET);

    // Parse ClassInfo to ServletInfo
    for (final ClassInfo clazz : classes) {
      try {
        final var classLocation = clazz.name().toString();
        LOGGER.log(DEBUG, format("Attempting to register servlet: %s", classLocation));
        final var servletComponent = new RegisterableServletComponent();
        final var classLoader = Thread.currentThread().getContextClassLoader();

        // Get URL Mapping from annotation
        final WebServlet servletAnnotation = WebServletAnnotationProcessor
            .getServletAnnotation(classLocation);
        if (servletAnnotation == null) {
          throw new AnnotationParseException("No WebServlet found for annotated class");
        }
        LOGGER.log(INFO, format("Registering servlet: %s at location: %s", classLocation, Arrays.toString(servletAnnotation.urlPatterns())));
        final ServletInfo deployableServletInfo = Servlets
            .servlet((Class<? extends Servlet>) classLoader.loadClass(classLocation))
            .addMappings(servletAnnotation.urlPatterns());

        // Fetch all servlet init-params
        for (final WebInitParam param : servletAnnotation.initParams()) {
          deployableServletInfo
              .addInitParam(param.name(), param.value());
        }

        servletComponent.setComponent(deployableServletInfo);
        components.add(servletComponent);
      } catch (final ClassNotFoundException | AnnotationParseException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public List<RegisterableComponent<ServletInfo>> getRegisterables () {
    return components;
  }
}
