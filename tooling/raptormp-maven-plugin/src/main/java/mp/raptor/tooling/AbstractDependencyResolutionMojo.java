package mp.raptor.tooling;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
public abstract class AbstractDependencyResolutionMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true, required = true)
  MavenProject project;

  @Parameter(defaultValue = "${session}", readonly = true, required = true)
  MavenSession session;

  @Component
  BuildPluginManager buildPluginManager;

  String outputDirectory;
  String finalName;

  void copyDependencies() throws MojoExecutionException {

    final var classesDir = project.getBuild().getOutputDirectory();

    executeMojo(
        plugin(
            groupId("org.apache.maven.plugins"),
            artifactId("maven-dependency-plugin"),
            version("3.1.1")
        ),
        goal("copy-dependencies"),
        configuration(
            element("includeScope", "runtime"),
            element("overWriteSnapshots", "true"),
            element("outputDirectory", classesDir + "/libs")
        ),
        executionEnvironment(project, session, buildPluginManager)
    );
  }

}
