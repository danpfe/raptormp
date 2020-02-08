package mp.raptor.tooling;

import mp.raptor.base.Runner;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

/**
 * Purpose:
 *
 * @author Hassan Nazar
 * @author www.hassannazar.net
 */
@Mojo(
    name = "package",
    threadSafe = true,
    defaultPhase = LifecyclePhase.PACKAGE,
    requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
    requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class PackageMojo extends AbstractPackagingMojo {

  @Parameter(defaultValue = "${project.build.directory}")
  private String outputDirectory;

  @Parameter(defaultValue = "${project.build.finalName}")
  private String finalName;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    assemble();
  }
}
