package mp.raptor.tooling;

import mp.raptor.base.Runner;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.jar.JarFile;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public abstract class AbstractPackagingMojo extends AbstractDependencyResolutionMojo {

  protected void assemble() throws MojoExecutionException {
    copyDependencies();
    unpackRunnableJars();
    createRunnable();
  }

  private void unpackRunnableJars() {
    // Find Runnable Jar
    final var path = Paths.get(project.getBuild().getOutputDirectory() + "/libs");
    try {
      Files.list(path).forEach(this::unwrapJar);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  private void unwrapJar(final Path jarPath) {
    try {
      final JarFile jar = new JarFile(jarPath.toFile());

      // Unwrap Runnable Jar
      jar.stream().parallel()
          .filter(jarEntry -> jarEntry.getName().toLowerCase().endsWith(".class"))
          .forEach(jarEntry -> {
            try {
              final Path outputPath = Paths.get(project.getBuild().getOutputDirectory(), jarEntry.getName());

              final Path outputPathParent = outputPath.getParent();

              if (outputPathParent != null) {

                Files.createDirectories(outputPathParent);
              }

              final InputStream inputStream = jar.getInputStream(jarEntry);

              Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);

              inputStream.close();
            } catch (final IOException e) {
              e.printStackTrace();
            }
          });
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  protected void createRunnable() throws MojoExecutionException {
    // Build runnable
    executeMojo(
        plugin(
            groupId("org.apache.maven.plugins"),
            artifactId("maven-jar-plugin"),
            version("3.2.0")
        ),
        goal("jar"),
        configuration(
            element("finalName", finalName),
            element("outputDirectory", outputDirectory),
            element("classifier", "uber"),
            element("forceCreation", "true"),
            element("archive",
                element("manifest",
                    /*element("addClasspath", "true"),
                    element("classpathPrefix", "libs/"),*/
                    element("mainClass", Runner.class.getCanonicalName())
                )
            )
        ),
        executionEnvironment(project, session, buildPluginManager)
    );
  }

}
