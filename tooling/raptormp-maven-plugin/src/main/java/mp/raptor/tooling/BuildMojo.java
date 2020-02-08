package mp.raptor.tooling;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jboss.jandex.Index;
import org.jboss.jandex.IndexWriter;
import org.jboss.jandex.Indexer;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;


/**
 * Purpose:
 * - Runs Annotation parser on the project
 * - Stores the created index file in the target classpath.
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Mojo(name = "build", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class BuildMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true, required = true)
  MavenProject project;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("Starting RaptorMP Build..");
    // Parse Jandex annotations
    final URLClassLoader urlClassLoader = getUrlClassLoader();
    final var indexer = parseAnnotations(urlClassLoader);
    storeAnnotations(indexer);
  }

  /**
   * @param index
   */
  private void storeAnnotations(final Index index) {
    final var indexPath = project.getBuild().getOutputDirectory() + "/index.idx";
    try (FileOutputStream outputStream = new FileOutputStream(indexPath)) {
      final var writer = new IndexWriter(outputStream);
      writer.write(index);
    } catch (final IOException e) {
      getLog().error(e);
    }

  }

  /**
   * Handles logic for parsing annotations from classpath.
   *
   * @param urlClassLoader classloader containing paths to compile-cp elements
   * @return indexer containing all classes with parsed annotations
   */
  Index parseAnnotations(final URLClassLoader urlClassLoader) {
    getLog().info("Running Annotation Parser");
    final Indexer indexer = new Indexer();
    try (ScanResult scanResult =
             new ClassGraph()
                 .verbose() // enable logging
                 .enableAllInfo() // Scan classes, methods, fields and annotations
                 .whitelistPackages(project.getGroupId()) // Scan x.y and subpackages
                 .addClassLoader(urlClassLoader)
                 .scan()) {

      // Populate index file
      for (final String classLocation : scanResult.getResourcesWithExtension(".class").getPaths()) {
        getLog().info("Found Class = " + classLocation);
        try (InputStream inputStream = urlClassLoader.getResourceAsStream(classLocation)) {
          indexer.index(inputStream);
        } catch (final IOException e) {
          getLog().error(e);
        }
      }

      return indexer.complete();
    }
  }

  /**
   * As Maven classpath dependencies from the project plugins are not resolved with the
   * restricted classpath during project compilation. We need to fetch and pass a
   * URLClassloader to the indexer, so as to find all the project-level classes.
   *
   * @return classloader containing paths to compile-cp elements
   * @throws MojoExecutionException
   */
  private URLClassLoader getUrlClassLoader() throws MojoExecutionException {
    // Scan over all classes under the project group-id
    getLog().info("Parsing classes under " + project.getGroupId());

    // Add compiled classpath element URI's
    final var paths = new ArrayList<URL>();
    try {
      for (final Object compilePath : project.getCompileClasspathElements()) {
        paths.add(new File((String) compilePath).toURI().toURL());
      }
    } catch (DependencyResolutionRequiredException | MalformedURLException e) {
      throw new MojoExecutionException("Failed to fetch classpath compile path!");
    }

    //Create a classloader and add all URL's
    return new URLClassLoader(paths.toArray(URL[]::new));
  }

}
